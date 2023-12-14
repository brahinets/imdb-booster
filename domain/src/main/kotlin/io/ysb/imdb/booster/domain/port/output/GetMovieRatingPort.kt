package io.ysb.imdb.booster.domain.port.output

import io.ysb.imdb.booster.domain.MovieId
import java.util.Optional

interface GetMovieRatingPort {
    fun getMovieRating(movieId: MovieId): Optional<Int>
}