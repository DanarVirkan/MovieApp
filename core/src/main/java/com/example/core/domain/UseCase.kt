package com.example.core.domain

import com.example.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun getMovieById(id: Int): Flow<Movie>
    fun getTVById(id: Int): Flow<TV>
    fun getTrending(): Flow<Resource<List<Item>>>
    fun getMovie(): Flow<List<Item>>
    fun getTV(): Flow<List<Item>>

    fun getError(): Flow<Boolean>
    fun getBookmarked(type: Int): Flow<List<Item>>
    fun updateBookmark(item: Item, newState: Boolean)
    fun removeAllBookmark()
}