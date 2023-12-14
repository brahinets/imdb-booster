package io.ysb.imdb.booster

import io.ysb.imdb.booster.domain.port.input.GetMovieUseCase
import io.ysb.imdb.booster.domain.port.input.SetMovieRatingUseCase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val movieApi = context.getBean(GetMovieUseCase::class.java)
    val ratingApi = context.getBean(SetMovieRatingUseCase::class.java)

    val movie = movieApi.getMovie("tt14524712")
    println(movie)
    println(ratingApi.setRating("tt14524712", 9))
    println(movieApi.getMovie("tt14524712"))
    println(ratingApi.setRating("tt14524712", movie.myRating!!))
}
