package com.moviedb.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.ui.TrendingAdapter
import com.moviedb.databinding.FragmentMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel: ExploreViewModel by viewModel()
    private lateinit var adapter: TrendingAdapter
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TrendingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMovie()
        binding.movieRv.adapter = adapter
        binding.movieRv.layoutManager = GridLayoutManager(context, 2)

        binding.refreshMovie.setOnClickListener {
            viewModel.setMovie()
            binding.movieProgress.visibility = View.VISIBLE
            errorHandler(false)
        }

        viewModel.getMovie().observe(viewLifecycleOwner, {
            binding.movieProgress.visibility = View.VISIBLE
            if (it != null) {
                adapter.setData(it)
                binding.movieRv.visibility = View.VISIBLE
                binding.movieProgress.visibility = View.GONE
            } else {
                binding.movieRv.visibility = View.GONE
            }
        })

        viewModel.getError().observe(viewLifecycleOwner, {
            errorHandler(it)
            binding.movieProgress.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.movieRv.adapter = null
        _binding = null
    }

    fun errorHandler(isError: Boolean) {
        binding.lottieAnimationView.visibility = if (isError) View.VISIBLE else View.GONE
        binding.errorMovie.visibility = if (isError) View.VISIBLE else View.GONE
        binding.refreshMovie.visibility = if (isError) View.VISIBLE else View.GONE
    }
}