package com.example.core.domain

data class TV(
    val id: Int,
    val poster: String,
    val name: String,
    val air_date: String,
    val genre: List<Genre>,
    val episode: Int,
    val season: Int,
    val vote: String,
    val overview: String,
    val isBookmarked: Boolean
)