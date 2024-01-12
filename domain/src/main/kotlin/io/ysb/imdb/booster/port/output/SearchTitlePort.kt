package io.ysb.imdb.booster.port.output

import java.util.Optional

fun interface SearchTitlePort {
    fun searchTitle(criteria: TitleSearchCriteria): Optional<TitleSuggestion>
}
