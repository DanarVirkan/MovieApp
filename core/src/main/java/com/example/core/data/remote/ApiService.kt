package com.example.core.data.remote

import com.example.core.data.remote.response.MovieResponse
import com.example.core.data.remote.response.Results
import com.example.core.data.remote.response.TVResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOWQ1NGVmNmFjZTY1ZWQ0NDNiMTk1MTAxNTQwMmQ4YiIsInN1YiI6IjVmMzY0NTI4YzRmNTUyMDAzNTA5NTBhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FkVaCeMvzkfP0Yc6NhSW7Y-2PoWtihSqRuEQOHwgwLs"
        const val IMG_URL = "https://image.tmdb.org/t/p/w500"
    }

    @GET("tv/{id}")
    fun getDetailsTV(@Path("id") id: Int): Call<TVResponse>

    @GET("movie/{id}")
    fun getDetailsMovie(@Path("id") id: Int): Call<MovieResponse>

    @GET("trending/all/week")
    suspend fun getTrendingPage(@Query("page") page: Int = 1): Results

    @GET("discover/movie")
    fun getMoviePage(@Query("page") page: Int = 1): Call<Results>

    @GET("discover/tv")
    fun getTVPage(@Query("page") page: Int = 1): Call<Results>

}
