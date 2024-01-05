package io.ysb.imdb.booster.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.domain.handler.TitleHandler
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType

private val SUPPORTED_TITLES: Array<TitleType> = arrayOf(TitleType.MOVIE)

class LoadingService(
    private val movieHandler: TitleHandler,
    private val videoGameHandler: TitleHandler,
    private val tvEpisodeHandler: TitleHandler,
    private val shortsHandler: TitleHandler
) : LoadRatingUseCase {
    private val logger = KotlinLogging.logger {}

    override fun loadRating(title: LoadingTitle) {
        logger.info { "Loading rating for ${title.id}" }

        when (title.type) {
            TitleType.MOVIE -> movieHandler.handle(title)
            TitleType.TV_EPISODE -> tvEpisodeHandler.handle(title)
            TitleType.VIDEO_GAME -> videoGameHandler.handle(title)
            TitleType.SHORT -> shortsHandler.handle(title)
            TitleType.VIDEO -> logger.info { "Skip loading ${title.id} (${title.type}). Video's require manual validation and should be processed manually" }
            TitleType.TV_MINI_SERIES, TitleType.TV_SHORT, TitleType.TV_MOVIE, TitleType.TV_SERIES
            -> logger.info { "Skip loading ${title.id} (${title.type}). TV's require manual validation and should be processed manually" }
            else -> logger.warn { "Skip loading rating '${title.myRating}' for ${title.id} '${title.name}' due to it is not supported type: ${title.type}. Only ${SUPPORTED_TITLES.contentToString()} are supported" }
        }
    }
}
