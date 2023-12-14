package io.ysb.imdb.booster.api.search

import io.ysb.imdb.booster.domain.Movie
import io.ysb.imdb.booster.domain.MovieId
import io.ysb.imdb.booster.domain.port.SearchMovieByIdPort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class SearchMovieByIdAdapter(val imdbClient: WebClient) : SearchMovieByIdPort {

    override fun searchMovieById(movieId: MovieId): Movie {
        val movieResponse = getMovieResponse(movieId).d.first()
        return Movie(
            movieResponse.id,
            movieResponse.title,
            movieResponse.year
        )
    }

    private fun getMovieResponse(movieId: MovieId): SuggestionModel {
        return imdbClient
            .get()
            .uri("https://v3.sg.media-imdb.com/suggestion/x/${movieId}.json")
            .retrieve()
            .bodyToMono(SuggestionModel::class.java)
            .block()!!
    }
}
