package io.ysb.imdb.booster.domain.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.domain.loader.MovieLoader
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType

class MovieHandler(
    private val movieLoader: MovieLoader
) : TitleHandler {

    private val logger = KotlinLogging.logger {}

    override fun handle(title: LoadingTitle) {
        if (title.type != TitleType.MOVIE) {
            logger.warn { "Skip handler '${title.id}' due to it is not supported by this handler" }
            return
        }

        movieLoader.loadTitle(title)
    }
}
