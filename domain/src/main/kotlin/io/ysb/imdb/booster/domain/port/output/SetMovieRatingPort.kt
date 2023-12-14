package io.ysb.imdb.booster.domain.port.output

import io.ysb.imdb.booster.domain.MovieId

interface SetMovieRatingPort {
    fun setMovieRating(movieId: MovieId, rating: Int)
}