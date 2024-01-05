package io.ysb.imdb.booster.domain.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort

class TvEpisodeHandler(
    private val movieHandler: TitleHandler,
    private val searchTitleByIdPort: SearchTitleByIdPort,
    private val searchMovieByName: SearchMovieByNamePort
) : TitleHandler {

    private val logger = KotlinLogging.logger {}

    override fun handle(title: LoadingTitle) {
        if (title.type != TitleType.TV_EPISODE) {
            logger.warn { "Skip handling '${title.id}' due to it is not supported by this handler" }
            return
        }

        val remoteById = searchTitleByIdPort.searchTitleById(title.id)
        logger.info { "TV-Episode ${remoteById.id} has name ${remoteById.title} and local name ${title.name} (${if (title.name == remoteById.title) "matched" else "not matched"}) and has local rating ${title.myRating}. Most likely it is some movie" }

        val movieToRate = searchMovieByName.searchMovieByName(remoteById.title)
        movieToRate.ifPresentOrElse({
            logger.info { "TV-Episode ${title.id} matches movie. Movie ${it.title} (${it.id}) will be rate to ${title.myRating}" }
            movieHandler.handle(
                LoadingTitle(
                    it.id,
                    it.title,
                    it.year,
                    title.myRating,
                    TitleType.MOVIE
                )
            )
        }, {
            logger.warn { "TV-Episode ${title.id} with name ${title.name} cannot be mapped and rated. Handle manually" }
        })
    }
}
