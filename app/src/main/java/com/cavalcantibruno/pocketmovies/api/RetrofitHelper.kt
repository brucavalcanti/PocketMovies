package com.cavalcantibruno.pocketmovies.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/?")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}