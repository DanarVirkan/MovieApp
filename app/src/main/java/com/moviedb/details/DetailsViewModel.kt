package com.moviedb.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Item
import com.example.core.domain.Movie
import com.example.core.domain.TV
import com.example.core.domain.UseCase
import com.example.core.utils.Constant.MOVIE
import com.example.core.utils.Constant.TV_SHOW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class DetailsViewModel(
    private val useCase: UseCase
) : ViewModel() {
    private val movie = MutableLiveData<Movie>()
    private val tv = MutableLiveData<TV>()

    fun getData(type: String?, id: Int) {
        when (type) {
            MOVIE -> {
                setMovie(id)
            }
            TV_SHOW -> {
                setTV(id)
            }
        }
    }

    private fun setMovie(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getMovieById(id).collect {
            movie.postValue(it)
        }
    }

    fun getMovie() = movie

    private fun setTV(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getTVById(id).collect {
            tv.postValue(it)
        }
    }

    fun getTV() = tv

    fun getError() = useCase.getError().asLiveData()

    fun updateBookmark(item: Item, newState: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        useCase.updateBookmark(item, newState)
    }

}