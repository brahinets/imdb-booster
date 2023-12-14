package io.ysb.imdb.booster.domain.port.output

import io.ysb.imdb.booster.domain.MovieId
import java.util.Optional

interface GetMovieScorePort {
    fun getMovieScore(movieId: MovieId): Optional<Int>
}