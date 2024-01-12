package io.ysb.imdb.booster.port.output

import java.util.Optional

fun interface SearchTitleByNamePort {
    fun searchTitleByName(titleName: String): Optional<TitleSuggestion>
}
