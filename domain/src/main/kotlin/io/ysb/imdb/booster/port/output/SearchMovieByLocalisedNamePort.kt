package io.ysb.imdb.booster.port.output

import java.util.Optional

fun interface SearchMovieByLocalisedNamePort {
    fun searchMovieByLocalisedName(titleName: String): Optional<TitleSuggestion>
}
