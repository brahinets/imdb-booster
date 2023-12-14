package io.ysb.imdb.booster.api

import io.ysb.imdb.booster.api.model.Model
import io.ysb.imdb.booster.domain.MovieId
import io.ysb.imdb.booster.domain.port.GetMovieScorePort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Component
class GetMovieScoreAdapter(val imdbClient: WebClient) : GetMovieScorePort {

    override fun getMovieScore(movieId: MovieId): Optional<Int> {
        val get: Model = getMovieResponse(movieId)

        val titles = get.data.titles
        if (titles.isEmpty()) {
            return Optional.empty()
        }

        val value = titles.first().userRating.value

        return Optional.of(value)
    }

    private fun getMovieResponse(movieId: MovieId): Model {
        return imdbClient.get()
            .uri { uriBuilder -> uriBuilder
                .query("operationName={operationName}&variables={variables}&extensions={extensions}")
                .build(mapOf(
                    Pair("operationName",  "TitlesUserRatings"),
                    Pair("variables",  "{\"idArray\":[\"$movieId\"]}"),
                    Pair("extensions",  "{\"persistedQuery\":{\"sha256Hash\":\"7895b806b91031c960384b9f47f276f45ecf5e817b5126b03ae754151c7bd530\",\"version\":1}}")
                ))
            }
            .retrieve()
            .bodyToMono(Model::class.java)
            .block()!!
    }
}
