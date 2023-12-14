package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.port.input.SetMovieRatingUseCase
import io.ysb.imdb.booster.domain.port.output.SetMovieRatingPort

class RatingService(
    private val setMovieRatingPort: SetMovieRatingPort
) : SetMovieRatingUseCase {

    override fun setRating(movieId: MovieId, score: Int) {
        setMovieRatingPort.setMovieRating(movieId, score)
    }
}