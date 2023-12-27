package io.ysb.imdb.booster.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.domain.handler.TitleHandler
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType

private val SUPPORTED_TITLES: Array<TitleType> = arrayOf(TitleType.MOVIE)

class LoadingService(
    private val movieHandler: TitleHandler,
    private val videoGameHandler: TitleHandler
) : LoadRatingUseCase {
    private val logger = KotlinLogging.logger {}

    override fun loadRating(title: LoadingTitle) {
        logger.info { "Loading rating for ${title.id}" }

        if (title.type == TitleType.MOVIE) {
            movieHandler.handle(title)
            return
        }

        if (title.type == TitleType.VIDEO_GAME) {
            videoGameHandler.handle(title)
            return
        }

        if (title.type == TitleType.TV_MINI_SERIES ||
            title.type == TitleType.TV_SHORT ||
            title.type == TitleType.TV_MOVIE
        ) {
            logger.info { "Skip loading ${title.id} (${title.type}). TV's require manual validation and should be processed manually" }
            return
        }

        logger.warn { "Skip loading rating '${title.myRating}' for ${title.id} '${title.name}' due to it is not supported type: ${title.type}. Only ${SUPPORTED_TITLES.contentToString()} are supported" }
    }
}
