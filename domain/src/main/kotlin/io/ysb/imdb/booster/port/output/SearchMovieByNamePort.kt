package io.ysb.imdb.booster.port.output

import java.util.Optional

fun interface SearchMovieByNamePort {
    fun searchMovieByName(titleName: String): Optional<TitleSuggestion>
}
