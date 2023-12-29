package io.ysb.imdb.booster.domain.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort

class ShortsHandler(
    private val movieHandler: MovieHandler,
    private val searchMovieByNamePort: SearchMovieByNamePort
) : TitleHandler {

    private val logger = KotlinLogging.logger {}

    override fun handle(title: LoadingTitle) {
        if (title.type != TitleType.SHORT) {
            logger.warn { "Skip handling '${title.id}' due to it is not supported by this handler" }
            return
        }

        logger.info { "Auto-mapping short ${title.id} and name '${title.name}' to movie" }
        val mappedMovie = searchMovieByNamePort.searchMovieByName(title.name)
        if (mappedMovie.isEmpty) {
            logger.warn { "Skip handling short ${title.id} '${title.name}' due to it can not be uniquely mapped to movie" }
            return
        }

        val movie = mappedMovie.get()
        logger.info { "Mapped short ${title.id} and name '${title.name}' to movie ${movie.id} with name '${movie.title}'" }

        logger.info { "Loading movie ${movie.id} '${movie.title}' instead of short ${title.id} '${title.name}'" }
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
