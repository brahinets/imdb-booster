package io.ysb.imdb.booster.domain.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort

class VideoGameHandler(
    private val searchMovieByNamePort: SearchMovieByNamePort,
    private val movieHandler: TitleHandler
) : TitleHandler {

    private val logger = KotlinLogging.logger {}

    override fun handle(title: LoadingTitle) {
        if (title.type != TitleType.VIDEO_GAME) {
            logger.warn { "Skip handling '${title.id}' due to it is not supported by this handler" }
            return
        }

        logger.info { "Auto-mapping game ${title.id} and name '${title.name}' to movie" }
        val movie = searchMovieByNamePort.searchMovieByName(title.name)
        logger.info { "Mapped game ${title.id} and name '${title.name}' to movie ${movie.id} with name '${movie.title}'" }

        logger.info { "Loading movie ${movie.id} '${movie.title}' instead of game ${title.id} '${title.name}'" }
        movieHandler.handle(
            LoadingTitle(
                id = movie.id,
                name = title.name,
                year = title.year,
                myRating = title.myRating,
                type = TitleType.MOVIE
            )
        )
    }
}
