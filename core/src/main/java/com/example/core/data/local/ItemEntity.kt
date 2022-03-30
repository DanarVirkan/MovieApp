package com.example.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val idGenerate: Long? = null,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "poster")
    val poster: String,
    val name: String? = null,
    val title: String? = null,
    @ColumnInfo(name = "vote")
    val vote: String,
    var isBookmarked: Boolean = false
)