package com.example.core.domain

import kotlinx.coroutines.flow.Flow


class Interactor(private val repo: IRepository) : UseCase {
    override fun getMovieById(id: Int) = repo.getMovieById(id)

    override fun getTVById(id: Int) = repo.getTVById(id)

    override fun getTrending() = repo.getTrending()

    override fun getMovie(): Flow<List<Item>> = repo.getMovie()

    override fun getTV(): Flow<List<Item>> = repo.getTV()

    override fun getError() = repo.getError()

    override fun getBookmarked(type: Int) = repo.getBookmarked(type)

    override fun updateBookmark(item: Item, newState: Boolean) {
        repo.updateBookmark(item, newState)
    }

    override fun removeAllBookmark() = repo.removeAllBookmark()
}