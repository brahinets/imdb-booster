package io.ysb.imdb.booster.domain.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort

class TvEpisodeHandler(
    private val searchTitleByIdPort: SearchTitleByIdPort
) : TitleHandler {

    private val logger = KotlinLogging.logger {}

    override fun handle(title: LoadingTitle) {
        if (title.type != TitleType.TV_EPISODE) {
            logger.warn { "Skip handling '${title.id}' due to it is not supported by this handler" }
            return
        }

        val remoteById = searchTitleByIdPort.searchTitleById(title.id)

        logger.info { "TV-Episode ${remoteById.id} has name ${remoteById.title} and local name ${title.name} (${ if(title.name == remoteById.title) "matched" else "not matched"}) and has local rating ${title.myRating}. Most likely it is some movie" }
    }
}
