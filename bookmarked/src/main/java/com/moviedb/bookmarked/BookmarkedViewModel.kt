package com.moviedb.bookmarked

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
class BookmarkedViewModel(private val useCase: UseCase) :
    ViewModel() {
    private var list = MutableLiveData<List<Item>>()
    private var selected = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            setList(0)
        }
    }

    fun getBookmarkedList() = useCase.getBookmarked(0).asLiveData()

    fun setList(type: Int) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getBookmarked(type).collect {
            list.postValue(it)
        }
    }

    fun getList() = list

    fun delete(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        useCase.updateBookmark(item, false)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        useCase.removeAllBookmark()
        setList(0)
        setSelected(0)
    }

    fun setSelected(item: Int) {
        selected.postValue(item)
    }

    fun getSelected() = selected
}