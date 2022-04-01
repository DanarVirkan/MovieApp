package com.example.core.data.local

import kotlinx.coroutines.flow.Flow

class LocalSource(
    private val dao: MovieDao
) {

    fun getTrending() = dao.getTrending()

    suspend fun insertTrending(item: List<ItemEntity>) = dao.insertAll(item)

    fun getBookmarked(type: Int): Flow<List<BookmarkedItem>> {
        return when (type) {
            0 -> dao.getAll()
            1 -> dao.getAllMovie()
            else -> dao.getAllTV()
        }
    }

    fun getBookmarkedById(id: Int): Boolean {
        return dao.getBookmarkedById(id)
    }

    fun updateBookmark(bookmarkedItem: BookmarkedItem, newState: Boolean) {
        bookmarkedItem.isBookmarked = newState
        dao.addBookmark(bookmarkedItem)
    }

    fun removeAllBookmark() {
        dao.deleteAllBookmark()
    }
}