package io.ysb.imdb.booster

import io.ysb.imdb.booster.domain.port.input.GetTitleUseCase
import io.ysb.imdb.booster.domain.port.input.RateTitleUseCase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val titleApi = context.getBean(GetTitleUseCase::class.java)
    val ratingApi = context.getBean(RateTitleUseCase::class.java)

    val title = titleApi.getTitle("tt14524712")
    println(title)
    println(ratingApi.rateTitle("tt14524712", 9))
    println(titleApi.getTitle("tt14524712"))
    println(ratingApi.rateTitle("tt14524712", title.myRating!!))
}
