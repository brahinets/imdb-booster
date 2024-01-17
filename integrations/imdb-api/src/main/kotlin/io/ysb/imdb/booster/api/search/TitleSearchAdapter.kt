package io.ysb.imdb.booster.api.search

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.SearchTitlePort
import io.ysb.imdb.booster.port.output.TitleSearchCriteria
import io.ysb.imdb.booster.port.output.TitleSuggestion
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

val CONFIDENT = listOf(TitleType.MOVIE, TitleType.TV_SERIES, TitleType.TV_MINI_SERIES, TitleType.TV_MOVIE)

@Component
class TitleSearchAdapter(val imdbClient: WebClient) : SearchTitlePort {

    private val logger = KotlinLogging.logger {}

    override fun searchTitle(criteria: TitleSearchCriteria): Optional<TitleSuggestion> {
        val suggestions: List<TitleSuggestion> = searchTitlesByLocalisedName(criteria)

        val bestLocalisedMatch = selectTheBestMatch(suggestions, criteria)
        if (bestLocalisedMatch.isPresent) {
            return bestLocalisedMatch
        }

        if (criteria.originalName.lowercase() != criteria.localisedName.lowercase()) {
            return selectTheBestMatch(searchTitlesByName(criteria), criteria)
        }

        return Optional.empty()
    }

    private fun selectTheBestMatch(
        suggestion: List<TitleSuggestion>,
        criteria: TitleSearchCriteria
    ): Optional<TitleSuggestion> {
        return if (suggestion.isEmpty()) {
            Optional.empty()
        } else if (suggestion.size > 1) {
            logger.warn { ("More than one title found for criteria $criteria") }
            suggestion.filter { it.year == criteria.year }.let {
                if (it.size == 1) {
                    Optional.of(it.first())
                } else {
                    logger.warn { ("More than one title found for criteria $criteria") }
                    Optional.empty()
                }
            }
        } else {
            Optional.of(suggestion.first())
        }
    }

    private fun searchTitlesByName(criteria: TitleSearchCriteria): List<TitleSuggestion> {
        val request = buildRequest(criteria.originalName, criteria.year)

        val response = doRequest(request)
        return mapResults(response.d, criteria.originalName, criteria.year)
    }

    private fun searchTitlesByLocalisedName(criteria: TitleSearchCriteria): List<TitleSuggestion> {
        val request = buildRequest(criteria.localisedName, criteria.year)
            .header("X-Imdb-User-Country", "RU")

        val response = doRequest(request)
        return mapResults(fixCyrillicHomoglyphs(response.d), criteria.localisedName, criteria.year)
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
        titleName: String,
        year: Int
    ): List<TitleSuggestion> {
        return autoSuggestItems
            .filter { it.title?.lowercase() == titleName.lowercase() }
            .filter { it.year in year - 1..year + 1 }
            .filter { TitleType.from(it.qid ?: "") in CONFIDENT }
            .map {
                TitleSuggestion(
                    it.id,
                    it.title!!,
                    it.year,
                    it.s?.split(",")?.toSet()
                )
            }
    }

    private fun fixCyrillicHomoglyphs(d: List<D>): List<D> {
        return d.map {
            it.copy(
                title = fixCyrillicHomoglyphs(it.title ?: "")
            )
        }
    }

    private fun fixCyrillicHomoglyphs(title: String): String {
        return title
            .replace("a", "а", true)
            .replace("e", "е", true)
            .replace("o", "о", true)
            .replace("i", "і", true)
    }
}
