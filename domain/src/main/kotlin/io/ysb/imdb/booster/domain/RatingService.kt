package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.port.input.SetTitleRatingUseCase
import io.ysb.imdb.booster.domain.port.output.SetTitleRatingPort

class RatingService(
    private val setTitleRatingPort: SetTitleRatingPort
) : SetTitleRatingUseCase {

    override fun setRating(titleId: TitleId, rating: Int) {
        setTitleRatingPort.setTitleRating(titleId, rating)
    }
}