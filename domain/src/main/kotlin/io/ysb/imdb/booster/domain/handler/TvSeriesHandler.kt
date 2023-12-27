package io.ysb.imdb.booster.domain.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort

class TvSeriesHandler(
    private val searchTitleByIdPort: SearchTitleByIdPort,
    private val searchMovieByNamePort: SearchMovieByNamePort
) : TitleHandler {

    private val logger = KotlinLogging.logger {}

    override fun handle(title: LoadingTitle) {
        if (title.type != TitleType.TV_SERIES) {
            logger.warn { "Skip handling '${title.id}' due to it is not supported by this handler" }
            return
        }

        val remoteById = searchTitleByIdPort.searchTitleById(title.id)
        val remoteByName = searchMovieByNamePort.searchMovieByName(title.name)

        logger.info { "TV-Series by id ${title.id} has local name '${title.name}' and remote name ${remoteById.title}" }
        logger.info { "TV-Series by name '${title.name}' has local id '${title.id}' and remote id ${remoteByName.takeIf { a -> a.isPresent }}" }
    }
}
