package io.ysb.imdb.booster.api.graphql

import io.ysb.imdb.booster.api.graphql.model.Model
import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Component
class GetTitleRatingAdapter(val imdbClient: WebClient) : GetTitleRatingPort {

    override fun getTitleRating(titleId: TitleId): Optional<Int> {
        val response: Model = doRequest(titleId)

        val titles = response.data.titles
        if (titles.isEmpty()) {
            return Optional.empty()
        }

        val value = titles.first().userRating.value

        return Optional.of(value)
    }

    private fun doRequest(titleId: TitleId): Model {
        return imdbClient.get()
            .uri { uriBuilder -> uriBuilder
                .query("operationName={operationName}&variables={variables}&extensions={extensions}")
                .build(mapOf(
                    Pair("operationName",  "TitlesUserRatings"),
                    Pair("variables",  "{\"idArray\":[\"$titleId\"]}"),
                    Pair("extensions",  "{\"persistedQuery\":{\"sha256Hash\":\"7895b806b91031c960384b9f47f276f45ecf5e817b5126b03ae754151c7bd530\",\"version\":1}}")
                ))
            }
            .retrieve()
            .bodyToMono(Model::class.java)
            .block()!!
    }
}
