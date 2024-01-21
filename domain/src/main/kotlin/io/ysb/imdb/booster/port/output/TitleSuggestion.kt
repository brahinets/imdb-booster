package io.ysb.imdb.booster.port.output

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.input.TitleType

data class TitleSuggestion(
    val id: TitleId,
    val title: String,
    val type: TitleType,
    val year: Int,
    val stars: Set<String>? = emptySet(),
)
