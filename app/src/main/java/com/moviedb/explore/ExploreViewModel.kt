package com.moviedb.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Item
import com.example.core.domain.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ExploreViewModel(private val useCase: UseCase) : ViewModel() {
    private val movieList = MutableLiveData<List<Item>>()
    private val tvList = MutableLiveData<List<Item>>()

    fun setMovie() = viewModelScope.launch(Dispatchers.IO) {
        useCase.getMovie().collect {
            movieList.postValue(it)
        }
    }

    fun setTV() = viewModelScope.launch(Dispatchers.IO) {
        useCase.getTV().collect {
            tvList.postValue(it)
        }
    }

    fun getMovie() = movieList

    fun getTV() = tvList

    fun getError() = useCase.getError().asLiveData()
}