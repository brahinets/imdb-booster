package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.port.input.RateTitleUseCase
import io.ysb.imdb.booster.domain.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.domain.port.output.SetTitleRatingPort

class RatingService(
    private val setTitleRatingPort: SetTitleRatingPort,
    private val getTitleRatingPort: GetTitleRatingPort
) : RateTitleUseCase {

    override fun rateTitle(titleId: TitleId, rating: Int) {
        val titleRating = getTitleRatingPort.getTitleRating(titleId)

        if (titleRating.isPresent) {
            return
        }

        setTitleRatingPort.setTitleRating(titleId, rating)
    }
}
