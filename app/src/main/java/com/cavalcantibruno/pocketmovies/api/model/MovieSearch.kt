package com.cavalcantibruno.pocketmovies.api.model

data class MovieSearch(
    val Response: String,
    val Search: List<SimpleMovie>,
    val totalResults: String
)