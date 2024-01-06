package io.ysb.imdb.booster

import io.ysb.imdb.booster.port.output.LoadLocalVotesPort
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val loader = context.getBean(LoadLocalVotesPort::class.java)

    Files.newBufferedReader(Paths.get("./votes.csv")).use { votesDump ->
        loader.loadLocalTitles(votesDump).forEach { println(it) }
    }
}
