package com.example.core.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<ItemEntity>)

    @Query("SELECT * FROM itementity")
    fun getTrending(): Flow<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(item: BookmarkedItem)

    @Query("SELECT * FROM bookmarkeditem WHERE isBookmarked=1")
    fun getAll(): Flow<List<BookmarkedItem>>

    @Query("SELECT * FROM bookmarkeditem WHERE NULLIF(name, '') IS NULL AND isBookmarked=1")
    fun getAllMovie(): Flow<List<BookmarkedItem>>

    @Query("SELECT * FROM bookmarkeditem WHERE NULLIF(title, '') IS NULL AND isBookmarked=1")
    fun getAllTV(): Flow<List<BookmarkedItem>>

    @Query("SELECT isBookmarked FROM bookmarkeditem WHERE id = :id AND isBookmarked=1")
    fun getBookmarkedById(id: Int): Boolean

    @Query("DELETE FROM bookmarkeditem")
    fun deleteAllBookmark()
}