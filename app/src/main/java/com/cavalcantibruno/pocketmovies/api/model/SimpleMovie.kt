package com.cavalcantibruno.pocketmovies.api.model

import java.io.Serializable

data class SimpleMovie(
    val Title:String,
    val Year:String,
    val imdbId:String,
    val Type:String,
    val Poster:String,

) : Serializable
