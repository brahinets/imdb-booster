package io.ysb.imdb.booster.domain.port.input

import io.ysb.imdb.booster.domain.TitleId

interface SetTitleRatingUseCase {
    fun setRating(titleId: TitleId, rating: Int)
}