package io.ysb.imdb.booster.domain.port.output

import io.ysb.imdb.booster.domain.MovieId

interface SearchMovieByIdPort {
    fun searchMovieById(movieId: MovieId): MovieSuggestion
}