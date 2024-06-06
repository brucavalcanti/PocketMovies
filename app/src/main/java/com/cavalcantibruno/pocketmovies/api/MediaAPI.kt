package com.cavalcantibruno.pocketmovies.api

import com.cavalcantibruno.pocketmovies.api.model.MovieData
import com.cavalcantibruno.pocketmovies.api.model.MovieSearch
import com.cavalcantibruno.pocketmovies.api.model.SimpleMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaAPI {
    @GET("?")
    suspend fun getDetailedTitle(
        @Query("t") title:String,
        @Query("apikey") apiKey:String
    ): Response<MovieData>

    @GET("?")
    suspend fun getMovieList(
        @Query("s") title:String,
        @Query("page") page:String,
        @Query("apikey") apiKey: String
    ): Response<MovieSearch>
}