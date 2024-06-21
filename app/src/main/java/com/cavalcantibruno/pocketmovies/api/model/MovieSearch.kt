package com.cavalcantibruno.pocketmovies.api.model

import java.io.Serializable

data class MovieSearch(
    val Response: String,
    val Search: List<SimpleMovie>,
    val totalResults: String
):Serializable