package io.ysb.imdb.booster.domain.port.input

import io.ysb.imdb.booster.domain.TitleId

interface RateTitleUseCase {
    fun rateTitle(titleId: TitleId, rating: Int)
}
