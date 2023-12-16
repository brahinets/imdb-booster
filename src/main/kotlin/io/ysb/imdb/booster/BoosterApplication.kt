package io.ysb.imdb.booster

import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val titleApi = context.getBean(LoadRatingUseCase::class.java)

    titleApi.loadRating(LoadingTitle("tt14524712", "Beckham", 2023, 8))
}
