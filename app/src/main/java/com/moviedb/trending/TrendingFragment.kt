package com.moviedb.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.TrendingAdapter
import com.moviedb.databinding.FragmentTrendingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TrendingFragment : Fragment() {

    private val viewModel: TrendingViewModel by viewModel()
    private lateinit var adapter: TrendingAdapter
    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TrendingAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTrending.adapter = adapter
        binding.rvTrending.layoutManager = GridLayoutManager(context, 2)

        binding.refreshTrending.setOnClickListener {
            parentFragmentManager.beginTransaction().detach(this).attach(this).commitNow()
        }

        viewModel.getTrending().observe(viewLifecycleOwner, {
            if (it != null) {
                binding.rvTrending.isVisible = it is Resource.Success
                binding.trendingProgress.isVisible = it is Resource.Loading
                binding.lottieAnimationView.isVisible = it is Resource.Error
                binding.errorTrending.isVisible = it is Resource.Error
                binding.refreshTrending.isVisible = it is Resource.Error
                if (it is Resource.Success) {
                    adapter.setData(it.data)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvTrending.adapter = null
        _binding = null
    }

}