package io.ysb.imdb.booster.domain.port

import io.ysb.imdb.booster.domain.Movie
import io.ysb.imdb.booster.domain.MovieId

interface SearchMovieByIdPort {
    fun searchMovieById(movieId: MovieId): Movie
}