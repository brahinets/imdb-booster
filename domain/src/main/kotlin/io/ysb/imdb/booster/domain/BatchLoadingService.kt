package io.ysb.imdb.booster.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.output.LoadLocalRatingsPort
import java.io.Reader

class BatchLoadingService(
    private val loadRatingUseCase: LoadRatingUseCase,
    private val loadLocalRatingsPort: LoadLocalRatingsPort
) : BatchLoadRatingUseCase {

    private val logger = KotlinLogging.logger {}

    override fun batchLoadRating(reader: Reader) {
        logger.info { "Start batch loading" }

        val loadLocalTitles = loadLocalRatingsPort.loadLocalTitles(reader)

        logger.info { "Found ${loadLocalTitles.size} titles" }

        loadLocalTitles.forEachIndexed { index, it ->
            logger.info { "Loading title ${it.name}. (${index + 1} of ${loadLocalTitles.size})" }

            loadRatingUseCase.loadRating(
                LoadingTitle(
                    id = it.id,
                    name = it.name,
                    year = it.year,
                    myRating = it.myRating
                )
            )
        }

        logger.info { "Batch loading finished" }
    }
}
