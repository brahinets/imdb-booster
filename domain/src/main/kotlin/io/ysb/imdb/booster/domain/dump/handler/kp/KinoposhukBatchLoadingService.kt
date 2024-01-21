package io.ysb.imdb.booster.domain.dump.handler.kp

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.output.KpLocalTitle
import io.ysb.imdb.booster.port.output.LoadLocalVotesPort
import io.ysb.imdb.booster.port.output.SearchTitlePort
import io.ysb.imdb.booster.port.output.TitleSearchCriteria
import java.io.Reader

class KinoposhukBatchLoadingService(
    private val searchTitlePort: SearchTitlePort,
    private val loadLocalRatingsPort: LoadLocalVotesPort
) : BatchLoadRatingUseCase {

    private val logger = KotlinLogging.logger {}

    override fun batchLoadRating(reader: Reader) {
        val loadLocalTitles = loadLocalRatingsPort.loadLocalTitles(reader)

        logger.info { "Found ${loadLocalTitles.size} Kinoposhuk titles" }

        loadLocalTitles.forEachIndexed { index, kp ->
            logger.info { "Loading title '${kp.localisedName}' (aka '${kp.originalName}'). (${index + 1} of ${loadLocalTitles.size})" }

            val imdb = mapToImdbTitle(kp)

            imdb.ifPresentOrElse(
                { logger.info { "Mapped Kinoposhuk title '${kp.originalName}' (${kp.year}) to IMDB title '${it.title}' (${it.year})" } },
                { logger.info { "Cannot map Kinoposhuk title '${kp.localisedName}' (${kp.year}) to Imdb title" } }
            )
        }

        logger.info { "Batch loading finished" }
    }

    private fun mapToImdbTitle(title: KpLocalTitle) = searchTitlePort.searchTitle(
        TitleSearchCriteria(
            title.originalName,
            title.localisedName,
            title.year
        )
    )
}
