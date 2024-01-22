package io.ysb.imdb.booster.domain.rating

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.TitleLoadingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.RateTitleUseCase

class TitleLoadingService(
    private val getTitleUseCase: GetTitleUseCase,
    private val rateTitleUseCase: RateTitleUseCase
) : TitleLoadingUseCase {

    private val logger = KotlinLogging.logger {}

    override fun loadRating(title: LoadingTitle) {
        logger.info { "Verifying correct title is rated by comparing local and remote data for ${title.id}" }
        val matched: Boolean = matchLocalAndRemote(title)

        if (matched.not()) {
            logger.warn { "Skip loading rating for ${title.id} due to it is not matched with site data" }
            return
        }

        rateTitleUseCase.rateTitle(title.id, title.myRating)
    }

    private fun matchLocalAndRemote(title: LoadingTitle): Boolean {
        val imdbTitle = getTitleUseCase.getTitle(title.id)

        if (title.name != imdbTitle.title) {
            logger.warn { "Skip loading rating for ${title.id} due to different names. Local '${title.name}' remote '${imdbTitle.title}'" }
            return false
        }

        if (title.year != imdbTitle.year) {
            logger.warn { "Skip loading rating for ${title.id} due to different year. Local '${title.year}' remote '${imdbTitle.year}'" }
            return false
        }

        return true
    }
}
