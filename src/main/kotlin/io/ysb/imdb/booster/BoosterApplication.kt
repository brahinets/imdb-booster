package io.ysb.imdb.booster

import io.ysb.imdb.booster.domain.port.input.GetMovieUseCase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val movieApi = context.getBean(GetMovieUseCase::class.java)

    println(movieApi.getMovie("tt14524712"))
}
