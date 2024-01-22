package io.ysb.imdb.booster.port.output

import java.io.Reader

fun interface ReadLocalRatingsPort {
    fun readLocalTitles(reader: Reader): List<ImdbLocalTitle>
}
