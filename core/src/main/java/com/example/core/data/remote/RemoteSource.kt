package com.example.core.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.core.data.remote.response.ItemResponse
import com.example.core.data.remote.response.MovieResponse
import com.example.core.data.remote.response.Results
import com.example.core.data.remote.response.TVResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteSource(private val api: ApiService) {
    private val error = MutableLiveData<Boolean>()

    fun getMovieById(id: Int): Flow<MovieResponse> {
        val movie = MutableLiveData<MovieResponse>()
        val client = api.getDetailsMovie(id)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val obj = response.body() as MovieResponse
                    movie.postValue(obj)
                    setError(false)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("REPO", t.toString())
                setError(true)
            }
        })
        return movie.asFlow()
    }

    fun getTVById(id: Int): Flow<TVResponse> {
        val tv = MutableLiveData<TVResponse>()
        val client = api.getDetailsTV(id)
        client.enqueue(object : Callback<TVResponse> {
            override fun onResponse(call: Call<TVResponse>, response: Response<TVResponse>) {
                if (response.isSuccessful) {
                    val obj = response.body() as TVResponse
                    tv.postValue(obj)
                    setError(false)
                }
            }

            override fun onFailure(call: Call<TVResponse>, t: Throwable) {
                Log.e("REPO", t.toString())
                setError(true)
            }

        })
        return tv.asFlow()
    }

    fun getTrending(): Flow<ApiResponse<List<ItemResponse>>> {
        return flow {
            try {
                val res = api.getTrendingPage(1).results
                if (res.isNotEmpty()) {
                    emit(ApiResponse.Success(res))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovie(): Flow<List<ItemResponse>> {
        val movieList = MutableLiveData<List<ItemResponse>>()
        val client = api.getMoviePage(1)
        client.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.isSuccessful) {
                    val obj = response.body() as Results
                    movieList.postValue(obj.results)
                    setError(false)
                }
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                setError(true)
            }

        })
        return movieList.asFlow()
    }

    fun getTV(): Flow<List<ItemResponse>> {
        val tvList = MutableLiveData<List<ItemResponse>>()
        val client = api.getTVPage(1)
        client.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.isSuccessful) {
                    val obj = response.body() as Results
                    tvList.postValue(obj.results)
                    setError(false)
                }
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                setError(true)
            }

        })
        return tvList.asFlow()
    }

    private fun setError(value: Boolean) {
        error.postValue(value)
    }

    fun getError() = error.asFlow()
}