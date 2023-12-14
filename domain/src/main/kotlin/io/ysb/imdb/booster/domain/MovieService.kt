package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.port.input.GetMovieUseCase
import io.ysb.imdb.booster.domain.port.input.Movie
import io.ysb.imdb.booster.domain.port.output.GetMovieRatingPort
import io.ysb.imdb.booster.domain.port.output.MovieSuggestion
import io.ysb.imdb.booster.domain.port.output.SearchMovieByIdPort

class MovieService(
    private val getMovieRatingPort: GetMovieRatingPort,
    private val searchMovieByIdPort: SearchMovieByIdPort
) : GetMovieUseCase {

    override fun getMovie(movieId: MovieId): Movie {
        val rating: Int? = getMovieRatingPort.getMovieRating(movieId).orElse(null)
        val info: MovieSuggestion = searchMovieByIdPort.searchMovieById(movieId)

        return Movie(
            info.id,
            info.title,
            info.year,
            rating
        )
    }
}