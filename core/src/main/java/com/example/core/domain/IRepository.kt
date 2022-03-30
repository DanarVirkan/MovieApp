package com.example.core.domain

import com.example.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getMovieById(id: Int): Flow<Movie>
    fun getTVById(id: Int): Flow<TV>
    fun getError(): Flow<Boolean>
    fun getTrending(): Flow<Resource<List<Item>>>
    fun getMovie(): Flow<List<Item>>
    fun getTV(): Flow<List<Item>>

    fun getBookmarked(type: Int): Flow<List<Item>>
    fun removeAllBookmark()
    fun updateBookmark(item: Item, newState: Boolean)
}