package io.ysb.imdb.booster.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.domain.loader.TitleLoader
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType

private val SUPPORTED_TITLES: Array<TitleType> = arrayOf(TitleType.MOVIE)

class LoadingService(
    private val movieLoader: TitleLoader
) : LoadRatingUseCase {
    private val logger = KotlinLogging.logger {}

    override fun loadRating(title: LoadingTitle) {
        logger.info { "Loading rating for ${title.id}" }

        if (title.type == TitleType.MOVIE) {
            movieLoader.loadTitle(title)
            return
        }

        logger.warn { "Skip loading rating for ${title.id} due to it is not supported type: ${title.type}. Only ${SUPPORTED_TITLES.contentToString()} are supported" }
    }
}
