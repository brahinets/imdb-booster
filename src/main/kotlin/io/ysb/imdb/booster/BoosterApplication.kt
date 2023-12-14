package io.ysb.imdb.booster

import io.ysb.imdb.booster.domain.port.GetMovieScorePort
import io.ysb.imdb.booster.domain.port.SearchMovieByIdPort
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val movieScoreApi = context.getBean(GetMovieScorePort::class.java)
    val movieApi = context.getBean(SearchMovieByIdPort::class.java)

    println(movieScoreApi.getMovieScore("tt14524712"))
    println(movieApi.searchMovieById("tt14524712"))
}
