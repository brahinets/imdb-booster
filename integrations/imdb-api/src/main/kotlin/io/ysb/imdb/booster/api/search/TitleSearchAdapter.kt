package io.ysb.imdb.booster.api.search

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.output.SearchTitlePort
import io.ysb.imdb.booster.port.output.TitleSearchCriteria
import io.ysb.imdb.booster.port.output.TitleSuggestion
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class TitleSearchAdapter(val imdbClient: WebClient) : SearchTitlePort {

    private val logger = KotlinLogging.logger {}

    override fun searchTitle(criteria: TitleSearchCriteria): Optional<TitleSuggestion> {
        val localTitle = criteria.originalName.lowercase() == criteria.localisedName.lowercase()
        val suggestion: List<TitleSuggestion> =
            if (localTitle) {
                searchTitlesByLocalisedName(criteria)
            } else {
                searchTitlesByName(criteria)
            }

        return if (suggestion.isEmpty()) {
            Optional.empty()
        } else if (suggestion.size > 1) {
            logger.warn { ("More than one title found for criteria $criteria") }
            Optional.empty()
        } else {
            Optional.of(suggestion.first())
        }
    }

    private fun searchTitlesByName(criteria: TitleSearchCriteria): List<TitleSuggestion> {
        val request = buildRequest(criteria.originalName, criteria.year)

        val response = doRequest(request)
        return mapResults(response.d, criteria.originalName)
    }

    private fun searchTitlesByLocalisedName(criteria: TitleSearchCriteria): List<TitleSuggestion> {
        val request = buildRequest(criteria.localisedName, criteria.year)
            .header("X-Imdb-User-Country", "RU")

        val response = doRequest(request)
        return mapResults(response.d, criteria.localisedName)
    }

    private fun doRequest(header: WebClient.RequestHeadersSpec<*>) =
        header
            .retrieve()
            .bodyToMono(SuggestionModel::class.java)
            .block()!!

    private fun buildRequest(titleId: TitleId, year: Int): WebClient.RequestHeadersSpec<*> {
        val builder = UriComponentsBuilder
            .fromHttpUrl("https://v3.sg.media-imdb.com/suggestion/x/{titleId} {year}.json")
            .buildAndExpand(titleId, year)

        return imdbClient.get()
            .uri(builder.toUri())
    }

    private fun mapResults(
        autoSuggestItems: List<D>,
        titleName: String
    ): List<TitleSuggestion> {
        return autoSuggestItems
            .filter { it.title?.lowercase() == titleName.lowercase() }
            .map {
                TitleSuggestion(
                    it.id,
                    it.title!!,
                    it.year,
                    it.s?.split(",")?.toSet()
                )
            }
    }
}