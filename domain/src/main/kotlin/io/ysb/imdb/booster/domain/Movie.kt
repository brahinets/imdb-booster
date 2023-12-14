package io.ysb.imdb.booster.domain

data class Movie(
    val id: MovieId,
    val title: String,
    val year: Int
)
