package io.ysb.imdb.booster.port.input

import java.io.Reader

fun interface BatchLoadRatingUseCase {
    fun batchLoadRating(reader: Reader)
}
