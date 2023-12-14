package io.ysb.imdb.booster.domain.port.output

import io.ysb.imdb.booster.domain.MovieId

data class MovieSuggestion(
    val id: MovieId,
    val title: String,
    val year: Int
)
