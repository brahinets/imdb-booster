package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.port.input.GetMovieUseCase
import io.ysb.imdb.booster.domain.port.input.Movie
import io.ysb.imdb.booster.domain.port.output.GetMovieScorePort
import io.ysb.imdb.booster.domain.port.output.MovieSuggestion
import io.ysb.imdb.booster.domain.port.output.SearchMovieByIdPort

class GetMovieService(
    private val getMovieScorePort: GetMovieScorePort,
    private val searchMovieByIdPort: SearchMovieByIdPort
) : GetMovieUseCase {

    override fun getMovie(movieId: MovieId): Movie {
        val score: Int? = getMovieScorePort.getMovieScore(movieId).orElse(null)
        val info: MovieSuggestion = searchMovieByIdPort.searchMovieById(movieId)

        return Movie(
            info.id,
            info.title,
            info.year,
            score
        )
    }
}