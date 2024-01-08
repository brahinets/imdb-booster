package io.ysb.imdb.booster

import io.ysb.imdb.booster.port.output.SearchMovieByLocalisedNamePort
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val search = context.getBean(SearchMovieByLocalisedNamePort::class.java)
    println(search.searchMovieByLocalisedName("Кин-Дза-Дза!"))
}
