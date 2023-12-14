package io.ysb.imdb.booster.domain.port.input

import io.ysb.imdb.booster.domain.MovieId

interface GetMovieUseCase {
    fun getMovie(movieId: MovieId): Movie
}