package io.ysb.imdb.booster

import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootApplication
class BoosterApplication

fun main(args: Array<String>) {
    val context = runApplication<BoosterApplication>(*args)

    val titleApi = context.getBean(BatchLoadRatingUseCase::class.java)

    Files.newBufferedReader(Paths.get("./ratings.csv")).use {
        titleApi.batchLoadRating(it)
    }
}
