package io.ysb.imdb.booster.port.output

import io.ysb.imdb.booster.domain.TitleId

interface SetTitleRatingPort {
    fun setTitleRating(titleId: TitleId, rating: Int)
}