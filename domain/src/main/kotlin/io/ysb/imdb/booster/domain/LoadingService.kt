package io.ysb.imdb.booster.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.MatchTitleUseCase
import io.ysb.imdb.booster.port.input.MatchingTitle
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.input.TitleType

private val SUPPORTED_TITLES: Array<TitleType> = arrayOf(TitleType.MOVIE)

class LoadingService(
    private val matchTitleUseCase: MatchTitleUseCase,
    private val getTitleUseCase: GetTitleUseCase,
    private val rateTitleUseCase: RateTitleUseCase,
) : LoadRatingUseCase {
    private val logger = KotlinLogging.logger {}

    override fun loadRating(title: LoadingTitle) {
        logger.info { "Loading rating for ${title.id}" }

        val matched: Boolean = matchLocalAndRemote(title)
        if (matched.not()) {
            logger.warn { "Skip loading rating for ${title.id} due to it is not matched with site data" }
            return
        }

        if (SUPPORTED_TITLES.contains(title.type).not()) {
            logger.warn { "Skip loading rating for ${title.id} due to it is not supported type: ${title.type}. Only ${SUPPORTED_TITLES.contentToString()} are supported" }
            return
        }

        logger.info { "Successfully matched ${title.id} with site data" }
        rateTitleUseCase.rateTitle(title.id, title.myRating)
    }

    private fun matchLocalAndRemote(title: LoadingTitle): Boolean {
        val imdbTitle = getTitleUseCase.getTitle(title.id)

        return matchTitleUseCase.matchTitle(
            MatchingTitle(title.name, title.year),
            MatchingTitle(imdbTitle.title, imdbTitle.year)
        )
    }
}
