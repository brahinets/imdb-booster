package io.ysb.imdb.booster.domain.dump.handler.imdb

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.LoadLocalRatingsPort
import java.io.Reader

private val SUPPORTED_TITLES: Array<TitleType> = arrayOf(TitleType.MOVIE)

class ImdbBatchLoadingService(
    private val loadRatingUseCase: LoadRatingUseCase,
    private val loadLocalRatingsPort: LoadLocalRatingsPort
) : BatchLoadRatingUseCase {

    private val logger = KotlinLogging.logger {}

    override fun batchLoadRating(reader: Reader) {
        val loadLocalTitles = loadLocalRatingsPort.loadLocalTitles(reader)

        logger.info { "Found ${loadLocalTitles.size} titles" }

        loadLocalTitles.forEachIndexed { index, it ->
            logger.info { "Loading title ${it.id} named '${it.name}'. (${index + 1} of ${loadLocalTitles.size})" }

            if (it.type !== TitleType.MOVIE) {
                logger.warn { "Skip loading rating '${it.myRating}' for ${it.id} '${it.name}' due to it is not supported type: ${it.type}. Only ${SUPPORTED_TITLES.contentToString()} are supported" }
            } else {
                loadRatingUseCase.loadRating(
                    LoadingTitle(
                        id = it.id,
                        name = it.name,
                        year = it.year,
                        myRating = it.myRating,
                        type = it.type
                    )
                )
            }
        }

        logger.info { "Batch loading ok IMDB dump has been finished" }
    }
}
