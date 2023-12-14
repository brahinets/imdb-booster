package io.ysb.imdb.booster.api

import io.ysb.imdb.booster.api.model.Model
import io.ysb.imdb.booster.domain.MovieId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


@Service
class ImdbApiClient(
    private val imdbWebClient: WebClient,
    @Value("\${imdb.api.session.cookie}") private val sessionCookie: String
) {
    fun get(movieId: MovieId): Model {
        val toUri: URI = UriComponentsBuilder
            .fromUriString("https://api.graphql.imdb.com/?operationName=TitlesUserRatings&variables={\"idArray\":[\"$movieId\"]}&extensions={\"persistedQuery\":{\"sha256Hash\":\"7895b806b91031c960384b9f47f276f45ecf5e817b5126b03ae754151c7bd530\",\"version\":1}}")
            .build().encode().toUri();

        return imdbWebClient.get()
            .uri(toUri)
            .header("Cookie", sessionCookie)
            .retrieve()
            .bodyToMono(Model::class.java)
            .block()!!
    }
}