package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("poster_path")
    val poster: String?,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("genres")
    val genre: List<GenreResponse>,
    @field:SerializedName("vote_average")
    val vote: String,
    @field:SerializedName("tagline")
    val tagline: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("release_date")
    val release_date: String,
    @field:SerializedName("runtime")
    val runtime: Int
)