package com.example.core.domain

import java.io.Serializable

data class Item(
    val idGenerate: Long? = null,
    val id: Int,
    val poster: String,
    val name: String? = null,
    val title: String? = null,
    val vote: String,
    val isBookmarked: Boolean = false
) : Serializable