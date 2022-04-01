package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class TVResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("poster_path")
    val poster: String?,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("first_air_date")
    val air_date: String,
    @field:SerializedName("genres")
    val genre: List<GenreResponse>,
    @field:SerializedName("number_of_episodes")
    val episode: Int,
    @field:SerializedName("number_of_seasons")
    val season: Int,
    @field:SerializedName("vote_average")
    val vote: String,
    @field:SerializedName("overview")
    val overview: String
)