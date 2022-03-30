package com.moviedb.trending

import androidx.lifecycle.*
import com.example.core.domain.UseCase

class TrendingViewModel(
    private val useCase: UseCase
) : ViewModel() {
    fun getTrending() = useCase.getTrending().asLiveData()
}