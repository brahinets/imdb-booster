package io.ysb.imdb.booster.api.search

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.output.SearchTitleByLocalisedNamePort
import io.ysb.imdb.booster.port.output.SearchTitleByNamePort
import io.ysb.imdb.booster.port.output.SearchTitlePort
import io.ysb.imdb.booster.port.output.TitleSearchCriteria
import io.ysb.imdb.booster.port.output.TitleSuggestion
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class TitleSearchAdapter(val imdbClient: WebClient) : SearchTitlePort, SearchTitleByNamePort,
    SearchTitleByLocalisedNamePort {

    override fun searchTitle(criteria: TitleSearchCriteria): Optional<TitleSuggestion> {
        val suggestion: Optional<TitleSuggestion> =
            if (criteria.originalName.lowercase() === criteria.localisedName.lowercase()) {
                searchTitleByLocalisedName(criteria.localisedName)
            } else {
                searchTitleByName(criteria.originalName)
            }

        return suggestion.filter { it.year == criteria.year }
    }

    override fun searchTitleByName(titleName: String): Optional<TitleSuggestion> {
        val request = buildRequest(titleName)

        val response = doRequest(request)
        return mapResults(response.d, titleName)
    }

    override fun searchTitleByLocalisedName(titleName: String): Optional<TitleSuggestion> {
        val request = buildRequest(titleName)
            .header("X-Imdb-User-Country", "RU")

        val response = doRequest(request)
        return mapResults(response.d, titleName)
    }

    private fun doRequest(header: WebClient.RequestHeadersSpec<*>) =
        header
            .retrieve()
            .bodyToMono(SuggestionModel::class.java)
            .block()!!

    private fun buildRequest(titleId: TitleId): WebClient.RequestHeadersSpec<*> {
        val builder = UriComponentsBuilder
            .fromHttpUrl("https://v3.sg.media-imdb.com/suggestion/x/{titleId}.json")
            .buildAndExpand(titleId)

        return imdbClient.get()
            .uri(builder.toUri())
    }

    private fun mapResults(
        autoSuggestItems: List<D>,
        titleName: String
    ): Optional<TitleSuggestion> {
        val result = autoSuggestItems.firstOrNull { it.title?.lowercase() == titleName.lowercase() }

        if (result == null) {
            return Optional.empty()
        }

        return Optional.of(
            TitleSuggestion(
                result.id,
                result.title!!,
                result.year,
                result.s?.split(",")?.toSet()
            )
        )
    }
}
