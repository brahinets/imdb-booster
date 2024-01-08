package io.ysb.imdb.booster.api.search

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.output.SearchMovieByLocalisedNamePort
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort
import io.ysb.imdb.booster.port.output.TitleSuggestion
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.net.URLEncoder
import java.nio.charset.StandardCharsets.UTF_8
import java.util.Optional

@Component
class SearchMovieByNameAdapter(val imdbClient: WebClient) : SearchMovieByNamePort, SearchMovieByLocalisedNamePort {

    override fun searchMovieByName(titleName: String): Optional<TitleSuggestion> {
        val request = buildRequest(titleName)

        val response = doRequest(request)
        return mapResults(response.d, titleName)
    }

    override fun searchMovieByLocalisedName(titleName: String): Optional<TitleSuggestion> {
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

    private fun buildRequest(titleId: TitleId): WebClient.RequestHeadersSpec<*> =
        imdbClient.get()
            .uri("https://v3.sg.media-imdb.com/suggestion/x/${URLEncoder.encode(titleId, UTF_8)}.json")

    private fun mapResults(
        autoSuggestItems: List<D>,
        titleName: String
    ): Optional<TitleSuggestion> {
        val result = autoSuggestItems.firstOrNull { it.title == titleName && it.qid == "movie" }

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
