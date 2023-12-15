package io.ysb.imdb.booster

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoosterApplication

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {

    val context = runApplication<BoosterApplication>(*args)

    val titleApi = context.getBean(GetTitleUseCase::class.java)
    val ratingApi = context.getBean(RateTitleUseCase::class.java)

    val title = titleApi.getTitle("tt14524712")
    logger.info { title }
    ratingApi.rateTitle("tt14524712", 9)
    logger.info { titleApi.getTitle("tt14524712") }
    ratingApi.rateTitle("tt14524712", title.myRating!!)
}
