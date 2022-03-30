package com.example.core.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<ItemEntity>)

    @Query("SELECT * FROM itementity")
    fun getTrending(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM itementity WHERE isBookmarked=1")
    fun getAll(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM itementity WHERE NULLIF(name, '') IS NULL AND isBookmarked=1")
    fun getAllMovie(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM itementity WHERE NULLIF(title, '') IS NULL AND isBookmarked=1")
    fun getAllTV(): Flow<List<ItemEntity>>

    @Update
    fun updateBookmark(item: ItemEntity)

    @Query("UPDATE itementity SET isBookmarked = 0 WHERE isBookmarked = 1")
    fun deleteAllBookmark()
}