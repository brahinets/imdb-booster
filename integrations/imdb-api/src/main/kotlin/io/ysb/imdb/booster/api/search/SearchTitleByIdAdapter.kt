package io.ysb.imdb.booster.api.search

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.port.output.TitleSuggestion
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class SearchTitleByIdAdapter(val imdbClient: WebClient) : SearchTitleByIdPort {

    override fun searchTitleById(titleId: TitleId): TitleSuggestion {
        val response = doRequest(titleId).d.first()

        return TitleSuggestion(
            response.id,
            response.title,
            response.year
        )
    }

    private fun doRequest(titleId: TitleId): SuggestionModel {
        return imdbClient
            .get()
            .uri("https://v3.sg.media-imdb.com/suggestion/x/${titleId}.json")
            .retrieve()
            .bodyToMono(SuggestionModel::class.java)
            .block()!!
    }
}
