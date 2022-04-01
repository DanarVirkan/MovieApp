package com.example.core.domain

import java.io.Serializable

data class Item(
    val id: Int,
    val poster: String,
    val name: String? = null,
    val title: String? = null,
    val vote: String,
) : Serializable