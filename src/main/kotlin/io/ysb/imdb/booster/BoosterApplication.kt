package io.ysb.imdb.booster

import io.ysb.imdb.booster.domain.dump.handler.kp.KinoposhukBatchLoadingService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val loader = context.getBean(KinoposhukBatchLoadingService::class.java)

    Files.newBufferedReader(Paths.get("./votes.csv")).use { votesDump ->
        loader.batchLoadRating(votesDump)
    }
}
