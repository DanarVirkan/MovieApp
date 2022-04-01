package com.example.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val idGenerate: Long? = null,
    @ColumnInfo(name = "id")
    override val id: Int,
    @ColumnInfo(name = "poster")
    override val poster: String,
    override val name: String? = null,
    override val title: String? = null,
    @ColumnInfo(name = "vote")
    override val vote: String
) : ItemDB()