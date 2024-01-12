package io.ysb.imdb.booster

import io.ysb.imdb.booster.port.output.LoadLocalVotesPort
import io.ysb.imdb.booster.port.output.SearchTitlePort
import io.ysb.imdb.booster.port.output.TitleSearchCriteria
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val search = context.getBean(SearchTitlePort::class.java)
    val loader = context.getBean(LoadLocalVotesPort::class.java)

    Files.newBufferedReader(Paths.get("./votes.csv")).use { votesDump ->
        loader.loadLocalTitles(votesDump).forEach { entry ->
            val title = search.searchTitle(
                TitleSearchCriteria(
                    entry.originalName,
                    entry.localisedName,
                    entry.year
                )
            )

            title.ifPresentOrElse(
                { println("Imdb Movie: ${it.title} (${it.year}) found for ${entry.localisedName} (name matched ${entry.localisedName == it.title})") },
                { println("Imdb Movie not found for: ${entry.localisedName}") }
            )
        }
    }
}
