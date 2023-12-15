package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.port.input.RateTitleUseCase
import io.ysb.imdb.booster.domain.port.output.SetTitleRatingPort

class RatingService(
    private val setTitleRatingPort: SetTitleRatingPort
) : RateTitleUseCase {

    override fun rateTitle(titleId: TitleId, rating: Int) {
        setTitleRatingPort.setTitleRating(titleId, rating)
    }
}
