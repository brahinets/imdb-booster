package io.ysb.imdb.booster.port.output

import io.ysb.imdb.booster.domain.TitleId

data class LocalTitle(
    val name: String,
    val id: TitleId,
    val myRating: Int,
    val year: Int,
    val genres: Set<String>
)
