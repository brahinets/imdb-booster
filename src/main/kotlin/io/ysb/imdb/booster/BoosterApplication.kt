package io.ysb.imdb.booster

import io.ysb.imdb.booster.port.output.LoadLocalVotesPort
import io.ysb.imdb.booster.port.output.SearchMovieByLocalisedNamePort
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val search = context.getBean(SearchMovieByNamePort::class.java)
    val searchLocalised = context.getBean(SearchMovieByLocalisedNamePort::class.java)
    val loader = context.getBean(LoadLocalVotesPort::class.java)

    Files.newBufferedReader(Paths.get("./votes.csv")).use { votesDump ->
        loader.loadLocalTitles(votesDump).forEach {entry ->
            val title = if(entry.name == entry.originalName)
                search.searchMovieByName(entry.name)
             else
                searchLocalised.searchMovieByLocalisedName(entry.name)

            title.ifPresentOrElse(
                { println("Imdb Movie: ${it.title} (${it.year}) found for ${entry.name} (name matched ${entry.name == it.title})") },
                { println("Imdb Movie not found for: ${entry.name}") }
            )
        }
    }
}
