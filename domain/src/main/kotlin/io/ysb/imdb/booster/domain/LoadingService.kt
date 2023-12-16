package io.ysb.imdb.booster.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.MatchTitleUseCase
import io.ysb.imdb.booster.port.input.MatchingTitle
import io.ysb.imdb.booster.port.input.RateTitleUseCase

class LoadingService(
    private val matchTitleUseCase: MatchTitleUseCase,
    private val getTitleUseCase: GetTitleUseCase,
    private val rateTitleUseCase: RateTitleUseCase,
) : LoadRatingUseCase {

    private val logger = KotlinLogging.logger {}

    override fun loadRating(title: LoadingTitle) {
        logger.info { "Loading rating for ${title.id}" }

        val matched: Boolean = matchLocalAndRemote(title)
        if (matched) {
            logger.info { "Successfully matched ${title.id} with site data. Rating it with ${title.myRating}" }
            rateTitleUseCase.rateTitle(title.id, title.myRating)
        } else {
            logger.info { "Skip loading rating for ${title.id} due to it is not matched with site data" }
        }
    }

    private fun matchLocalAndRemote(title: LoadingTitle): Boolean {
        val imdbTitle = getTitleUseCase.getTitle(title.id)

        return matchTitleUseCase.matchTitle(
            MatchingTitle(title.name, title.year),
            MatchingTitle(imdbTitle.title, imdbTitle.year)
        )
    }
}
