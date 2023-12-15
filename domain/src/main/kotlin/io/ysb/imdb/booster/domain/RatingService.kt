package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.SetTitleRatingPort

class RatingService(
    private val setTitleRatingPort: SetTitleRatingPort,
    private val getTitleRatingPort: GetTitleRatingPort
) : RateTitleUseCase {

    override fun rateTitle(titleId: TitleId, rating: Int) {
        val titleRating = getTitleRatingPort.getTitleRating(titleId)

        if (titleRating.isPresent) {
            return
        }

        if(rating < 1 || rating > 10) {
            throw IllegalArgumentException("Rate must be between 1 and 10 (inclusive)")
        }

        setTitleRatingPort.setTitleRating(titleId, rating)
    }
}
