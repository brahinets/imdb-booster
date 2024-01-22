package io.ysb.imdb.booster.port.input

import io.ysb.imdb.booster.domain.TitleId

data class LoadingTitle(
    val id: TitleId,
    val name: String,
    val year: Int,
    val myRating: Int
)
