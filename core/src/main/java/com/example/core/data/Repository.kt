package com.example.core.data

import com.example.core.data.local.LocalSource
import com.example.core.data.remote.ApiResponse
import com.example.core.data.remote.RemoteSource
import com.example.core.data.remote.response.ItemResponse
import com.example.core.domain.IRepository
import com.example.core.domain.Item
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    private val local: LocalSource,
    private val remote: RemoteSource
) :
    IRepository {

    // REMOTE
    override fun getMovieById(id: Int) = remote.getMovieById(id).map {
        val isBookmarked = getBookmarkedById(id)
        DataMapper.mapResponseToMovie(it, isBookmarked)
    }

    override fun getTVById(id: Int) = remote.getTVById(id).map {
        val isBookmarked = getBookmarkedById(id)
        DataMapper.mapResponseToTV(it, isBookmarked)
    }

    override fun getTrending(): Flow<Resource<List<Item>>> =
        object : NetworkBoundResource<List<Item>, List<ItemResponse>>() {
            override fun loadFromDB(): Flow<List<Item>> =
                local.getTrending().map { DataMapper.mapEntitiesToDomain(it) }

            override fun shouldFetch(data: List<Item>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ItemResponse>>> =
                remote.getTrending()

            override suspend fun saveCallResult(data: List<ItemResponse>) {
                val converter = DataMapper.mapResponseToEntities(data)
                local.insertTrending(converter)
            }
        }.asFlow()

    override fun getMovie() = remote.getMovie().map {
        DataMapper.mapResponseToDomain(it)
    }

    override fun getTV() = remote.getTV().map {
        DataMapper.mapResponseToDomain(it)
    }

    override fun getError(): Flow<Boolean> = remote.getError()

    // LOCAL
    override fun getBookmarked(type: Int): Flow<List<Item>> =
        local.getBookmarked(type).map { DataMapper.mapEntitiesToDomain(it) }

    override fun updateBookmark(item: Item, newState: Boolean) {
        val converter = DataMapper.mapDomainToBookmarkedItem(item)
        local.updateBookmark(converter, newState)
    }

    private fun getBookmarkedById(id: Int): Boolean {
        return local.getBookmarkedById(id)
    }

    override fun removeAllBookmark() = local.removeAllBookmark()
}