package com.example.core.domain

data class Movie(
    val id: Int,
    val poster: String,
    val title: String,
    val genre: List<Genre>,
    val vote: String,
    val tagline: String,
    val overview: String,
    val release_date: String,
    val runtime: Int
)