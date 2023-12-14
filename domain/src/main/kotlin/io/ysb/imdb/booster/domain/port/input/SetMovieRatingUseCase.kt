package io.ysb.imdb.booster.domain.port.input

import io.ysb.imdb.booster.domain.MovieId

interface SetMovieRatingUseCase {
    fun setRating(movieId: MovieId, score: Int)
}