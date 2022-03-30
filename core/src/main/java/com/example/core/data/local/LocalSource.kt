package com.example.core.data.local

import kotlinx.coroutines.flow.Flow

class LocalSource(
    private val dao: MovieDao
) {

    fun getTrending() = dao.getTrending()

    suspend fun insertTrending(item: List<ItemEntity>) = dao.insertAll(item)

    fun getBookmarked(type: Int): Flow<List<ItemEntity>> {
        return when (type) {
            0 -> dao.getAll()
            1 -> dao.getAllMovie()
            else -> dao.getAllTV()
        }
    }

    fun updateBookmark(itemEntity: ItemEntity, newState: Boolean) {
        itemEntity.isBookmarked = newState
        dao.updateBookmark(itemEntity)
    }

    fun removeAllBookmark() {
        dao.deleteAllBookmark()
    }
}