package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("poster_path")
    val poster: String,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("vote_average")
    val vote: String
)