package io.ysb.imdb.booster.api.search

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort
import io.ysb.imdb.booster.port.output.TitleSuggestion
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.net.URLEncoder
import java.nio.charset.StandardCharsets.UTF_8
import java.util.Optional

@Component
class SearchMovieByNameAdapter(val imdbClient: WebClient) : SearchMovieByNamePort {

    override fun searchMovieByName(titleName: String): Optional<TitleSuggestion> {
        val autoSuggestItems = doRequest(titleName).d
        val result = autoSuggestItems.firstOrNull { it.title == titleName && it.qid == "movie" }

        if (result == null) {
            return Optional.empty()
        }

        return Optional.of(
            TitleSuggestion(
                result.id,
                result.title!!,
                result.year
            )
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
