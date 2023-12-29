package io.ysb.imdb.booster.port.output

import io.ysb.imdb.booster.domain.TitleId

data class TitleSuggestion(
    val id: TitleId,
    val title: String,
    val year: Int,
    val stars: Set<String>? = emptySet(),
)
