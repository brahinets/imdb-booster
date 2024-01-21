package io.ysb.imdb.booster.port.input

import io.ysb.imdb.booster.domain.TitleId

data class Title(
    val id: TitleId,
    val type: TitleType,
    val title: String,
    val year: Int,
    val myRating: Int?
)
