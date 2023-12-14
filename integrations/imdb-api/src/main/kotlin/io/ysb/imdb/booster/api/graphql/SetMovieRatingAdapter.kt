package io.ysb.imdb.booster.api.graphql

import io.ysb.imdb.booster.api.graphql.model.UpdateRatingResponse
import io.ysb.imdb.booster.domain.MovieId
import io.ysb.imdb.booster.domain.port.output.SetMovieRatingPort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class SetMovieRatingAdapter(val imdbClient: WebClient) : SetMovieRatingPort {

    override fun setMovieRating(movieId: MovieId, rating: Int) {
        doRequest(movieId, rating)
    }

    private fun doRequest(movieId: MovieId, rating: Int): UpdateRatingResponse {
        return imdbClient.post()
            .body(
                BodyInserters.fromValue(
                    "{\"query\":\"mutation UpdateTitleRating(\$rating: Int!, \$titleId: ID!) {  rateTitle(input: {rating: \$rating, titleId: \$titleId}) {    rating {      value      __typename    }    __typename  }}\",\"operationName\":\"UpdateTitleRating\",\"variables\":{\"rating\":$rating,\"titleId\":\"$movieId\"}}"
                )
            )
            .retrieve()
            .bodyToMono(UpdateRatingResponse::class.java)
            .block()!!
    }
}
