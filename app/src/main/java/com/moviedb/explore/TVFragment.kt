package com.moviedb.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.ui.TrendingAdapter
import com.moviedb.databinding.FragmentTvBinding
import org.koin.android.viewmodel.ext.android.viewModel


class TVFragment : Fragment() {

    private val viewModel: ExploreViewModel by viewModel()
    private lateinit var adapter: TrendingAdapter
    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TrendingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setTV()
        binding.tvRv.adapter = adapter
        binding.tvRv.layoutManager = GridLayoutManager(context, 2)

        binding.refreshTv.setOnClickListener {
            viewModel.setTV()
            binding.tvProgress.visibility = View.VISIBLE
            errorHandler(false)
        }

        viewModel.getTV().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setData(it)
                binding.tvRv.visibility = View.VISIBLE
                binding.tvProgress.visibility = View.GONE
            } else {
                binding.tvRv.visibility = View.GONE
            }
        })

        viewModel.getError().observe(viewLifecycleOwner, {
            errorHandler(it)
            binding.tvProgress.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.tvRv.adapter = null
        _binding = null
    }

    fun errorHandler(isError: Boolean) {
        binding.lottieAnimationView.visibility = if (isError) View.VISIBLE else View.GONE
        binding.errorTv.visibility = if (isError) View.VISIBLE else View.GONE
        binding.refreshTv.visibility = if (isError) View.VISIBLE else View.GONE
    }

}