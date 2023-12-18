package io.ysb.imdb.booster.port.output

import java.io.Reader

fun interface LoadLocalRatingsPort {
    fun loadLocalTitles(reader: Reader): List<LocalTitle>
}
