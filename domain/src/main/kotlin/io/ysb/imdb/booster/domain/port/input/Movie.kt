package io.ysb.imdb.booster.domain.port.input

import io.ysb.imdb.booster.domain.MovieId

data class Movie(
    val id: MovieId,
    val title: String,
    val year: Int,
    val myRating: Int?
)
