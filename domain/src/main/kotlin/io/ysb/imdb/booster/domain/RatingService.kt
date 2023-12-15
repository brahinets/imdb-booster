package io.ysb.imdb.booster.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.SetTitleRatingPort

class RatingService(
    private val setTitleRatingPort: SetTitleRatingPort,
    private val getTitleRatingPort: GetTitleRatingPort
) : RateTitleUseCase {

    private val logger = KotlinLogging.logger {}

    override fun rateTitle(titleId: TitleId, rating: Int) {
        logger.info { "Rating $titleId to $rating" }

        val titleRating = getTitleRatingPort.getTitleRating(titleId)
        if (titleRating.isPresent) {
            logger.info { "Skip rating $titleId due to it is already rated" }
            return
        }

        if (rating < 1 || rating > 10) {
            throw IllegalArgumentException("Rate must be between 1 and 10 (inclusive)")
        }

        setTitleRatingPort.setTitleRating(titleId, rating)
        logger.info { "Rated $titleId to $rating" }
    }
}
