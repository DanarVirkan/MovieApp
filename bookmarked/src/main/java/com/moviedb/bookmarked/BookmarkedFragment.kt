package com.moviedb.bookmarked

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.Item
import com.example.core.ui.BookmarkedAdapter
import com.moviedb.bookmarked.databinding.FragmentBookmarkedBinding
import com.moviedb.bookmarked.di.bookmarkedModule
import com.shashank.sony.fancytoastlib.FancyToast
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@InternalCoroutinesApi
class BookmarkedFragment : Fragment() {
    private val viewModel: BookmarkedViewModel by viewModel()
    private lateinit var filterDialog: AlertDialog.Builder
    private lateinit var listItem: List<Item>
    private var selected = 0
    private var _binding: FragmentBookmarkedBinding? = null
    private val binding get() = _binding!!

    private val swipeListener: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = listItem[viewHolder.absoluteAdapterPosition]
                viewModel.delete(
                    item
                )
                FancyToast.makeText(
                    context,
                    getString(com.moviedb.R.string.remove_bookmark),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    R.drawable.remove,
                    false
                ).show()
                viewModel.setList(selected)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).addBackgroundColor(setBgColor())
                    .addSwipeLeftLabel(resources.getString(R.string.remove))
                    .setSwipeLeftLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f)
                    .setSwipeLeftLabelColor(setTextColor())
                    .addSwipeRightLabel(resources.getString(R.string.remove))
                    .setSwipeRightLabelColor(setTextColor())
                    .setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f)
                    .addActionIcon(R.drawable.remove).create().decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

    private fun color(color: Int): Int {
        return ContextCompat.getColor(
            requireContext(),
            color
        )
    }

    private fun setTextColor(): Int = color(com.moviedb.R.color.colorAccent)
    private fun setBgColor(): Int = color(com.moviedb.R.color.colorPrimary)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(bookmarkedModule)
        filterDialog = AlertDialog.Builder(context)
    }

    override fun onStart() {
        super.onStart()
        viewModel.setList(0)
        viewModel.setSelected(0)

        viewModel.getBookmarkedList().observe(viewLifecycleOwner, {
            binding.removeAll.isVisible = it.isNotEmpty()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filter.setOnClickListener {
            filterDialog.show()
        }
        binding.removeAll.setOnClickListener {
            val deleteDialog = AlertDialog.Builder(context)
            deleteDialog.setMessage(R.string.remove_title)
            deleteDialog.setPositiveButton(R.string.ok) { dialog, _ ->
                viewModel.deleteAll()
                FancyToast.makeText(
                    context,
                    getString(R.string.remove_bookmark_all),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    R.drawable.remove,
                    false
                ).show()
                dialog.dismiss()
            }
            deleteDialog.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            deleteDialog.create().show()
        }
        viewModel.getList().observe(viewLifecycleOwner, {
            listItem = it
            if (it.isNotEmpty()) {
                binding.rvBookmarked.adapter = BookmarkedAdapter(it)
                binding.rvBookmarked.layoutManager = LinearLayoutManager(context)

                val touchHelper = ItemTouchHelper(swipeListener)
                touchHelper.attachToRecyclerView(binding.rvBookmarked)
            }
            binding.rvBookmarked.isVisible = it.isNotEmpty()
            binding.noData.isVisible = it.isEmpty()
            binding.emptyAnimation.isVisible = it.isEmpty()
        })

        viewModel.getSelected().observe(viewLifecycleOwner, {
            filterDialog.setTitle(R.string.dialog_title)
            filterDialog.setSingleChoiceItems(
                R.array.filter,
                it
            ) { dialog, i ->
                viewModel.setList(i)
                viewModel.setSelected(i)
                dialog.dismiss()
            }
            selected = it
            when (it) {
                0 -> binding.noData.text = resources.getText(R.string.no_data_all)
                1 -> binding.noData.text = resources.getText(R.string.no_data_movie)
                2 -> binding.noData.text = resources.getText(R.string.no_data_tv)
            }
            filterDialog.create()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvBookmarked.adapter = null
        _binding = null
    }
}