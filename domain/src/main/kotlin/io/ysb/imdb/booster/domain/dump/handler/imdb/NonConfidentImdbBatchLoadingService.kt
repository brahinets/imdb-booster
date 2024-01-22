package io.ysb.imdb.booster.domain.dump.handler.imdb

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleLoadingUseCase
import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.ReadLocalRatingsPort
import java.io.Reader

private val CONFIDENT_TITLES: Array<TitleType> = arrayOf(TitleType.MOVIE)

class NonConfidentImdbBatchLoadingService(
    private val titleLoadingUseCase: TitleLoadingUseCase,
    private val readLocalRatingsPort: ReadLocalRatingsPort
) : BatchLoadRatingUseCase {

    private val logger = KotlinLogging.logger {}

    override fun batchLoadRating(reader: Reader) {
        val localTitles = readLocalRatingsPort.readLocalTitles(reader)

        logger.info { "Found ${localTitles.size} titles" }

        localTitles.forEachIndexed { index, it ->
            logger.info { "Loading title ${it.id} named '${it.name}'. (${index + 1} of ${localTitles.size})" }

            if (it.type !== TitleType.MOVIE) {
                logger.warn { "Skip loading rating '${it.myRating}' for ${it.id} '${it.name}' due to it is not supported type: ${it.type}. Only ${CONFIDENT_TITLES.contentToString()} are supported" }
            } else {
                titleLoadingUseCase.loadRating(
                    LoadingTitle(
                        id = it.id,
                        name = it.name,
                        year = it.year,
                        myRating = it.myRating
                    )
                )
            }
        }

        logger.info { "Batch loading ok IMDB dump has been finished" }
    }
}
