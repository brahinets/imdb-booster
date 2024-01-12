package io.ysb.imdb.booster.port.output

import java.util.Optional

fun interface SearchTitleByLocalisedNamePort {
    fun searchTitleByLocalisedName(titleName: String): Optional<TitleSuggestion>
}
