package io.ysb.imdb.booster.domain.dump.handler.kp

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.domain.rating.TitleInfoService
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.TitleLoadingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.output.KpLocalTitle
import io.ysb.imdb.booster.port.output.ReadLocalVotesPort
import io.ysb.imdb.booster.port.output.SearchTitlePort
import io.ysb.imdb.booster.port.output.TitleSearchCriteria
import java.io.Reader

class KinoposhukBatchLoadingService(
    private val searchTitlePort: SearchTitlePort,
    private val titleLoadingUseCase: TitleLoadingUseCase,
    private val titleInfoService: TitleInfoService,
    private val readLocalRatingsPort: ReadLocalVotesPort
) : BatchLoadRatingUseCase {

    private val logger = KotlinLogging.logger {}

    override fun batchLoadRating(reader: Reader) {
        val localTitles = readLocalRatingsPort.readLocalTitles(reader)

        logger.info { "Found ${localTitles.size} Kinoposhuk titles" }

        localTitles.forEachIndexed { index, kp ->
            logger.info { "Loading title '${kp.localisedName}' (aka '${kp.originalName}'). (${index + 1} of ${localTitles.size})" }

            val imdb = mapToImdbTitle(kp)

            imdb.ifPresentOrElse(
                {
                    logger.info { "Mapped Kinoposhuk title '${kp.originalName}' (${kp.year}) to IMDB title ${it.id} '${it.title}' (${it.year})" }

                    val title = titleInfoService.getTitle(it.id)

                    titleLoadingUseCase.loadRating(
                        LoadingTitle(
                            title.id,
                            title.title,
                            title.year,
                            kp.myRating
                        )
                    )

                },
                { logger.warn { "Cannot map Kinoposhuk title '${kp.localisedName}' (${kp.year}) to Imdb title" } }
            )
        }

        logger.info { "Batch loading ok Kinoposhuk dump has been finished" }
    }

    private fun mapToImdbTitle(title: KpLocalTitle) = searchTitlePort.searchTitle(
        TitleSearchCriteria(
            title.originalName,
            title.localisedName,
            title.year
        )
    )
}
