package com.example.core.utils

import com.example.core.data.local.BookmarkedItem
import com.example.core.data.local.ItemDB
import com.example.core.data.local.ItemEntity
import com.example.core.data.remote.response.GenreResponse
import com.example.core.data.remote.response.ItemResponse
import com.example.core.data.remote.response.MovieResponse
import com.example.core.data.remote.response.TVResponse
import com.example.core.domain.Genre
import com.example.core.domain.Item
import com.example.core.domain.Movie
import com.example.core.domain.TV

object DataMapper {
    private fun mapResponseToGenre(input: List<GenreResponse>): List<Genre> = input.map {
        Genre(
            name = it.name
        )
    }

    fun mapResponseToMovie(input: MovieResponse, isBookmarked: Boolean): Movie = Movie(
        id = input.id,
        poster = input.poster ?: "none",
        title = input.title,
        genre = mapResponseToGenre(input.genre),
        vote = input.vote,
        tagline = input.tagline,
        overview = input.overview,
        release_date = input.release_date,
        runtime = input.runtime,
        isBookmarked = isBookmarked
    )

    fun mapResponseToTV(input: TVResponse, isBookmarked: Boolean): TV = TV(
        id = input.id,
        poster = input.poster ?: "none",
        name = input.name,
        air_date = input.air_date,
        genre = mapResponseToGenre(input.genre),
        episode = input.episode,
        season = input.season,
        vote = input.vote,
        overview = input.overview,
        isBookmarked = isBookmarked
    )

    fun mapResponseToDomain(input: List<ItemResponse>): List<Item> = input.map {
        Item(
            id = it.id,
            poster = it.poster ?: "none",
            name = it.name,
            title = it.title,
            vote = it.vote,
        )
    }

    fun mapResponseToEntities(input: List<ItemResponse>): List<ItemEntity> =
        input.map {
            ItemEntity(
                id = it.id,
                poster = it.poster ?: "none",
                name = it.name,
                title = it.title,
                vote = it.vote
            )
        }

    fun mapEntitiesToDomain(input: List<ItemDB>): List<Item> =
        input.map {
            Item(
                id = it.id,
                poster = it.poster,
                name = it.name,
                title = it.title,
                vote = it.vote,
            )
        }

    fun mapDomainToBookmarkedItem(input: Item): BookmarkedItem =
        BookmarkedItem(
            id = input.id,
            poster = input.poster,
            name = input.name,
            title = input.title,
            vote = input.vote
        )
}