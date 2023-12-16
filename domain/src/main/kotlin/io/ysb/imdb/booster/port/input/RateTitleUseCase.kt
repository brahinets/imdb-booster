package io.ysb.imdb.booster.port.input

import io.ysb.imdb.booster.domain.TitleId

fun interface RateTitleUseCase {
    fun rateTitle(titleId: TitleId, rating: Int)
}
