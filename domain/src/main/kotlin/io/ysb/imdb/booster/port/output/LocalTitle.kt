package io.ysb.imdb.booster.port.output

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.input.TitleType

data class LocalTitle(
    val name: String,
    val id: TitleId,
    val myRating: Int,
    val type: TitleType,
    val year: Int,
    val genres: Set<String>
)
