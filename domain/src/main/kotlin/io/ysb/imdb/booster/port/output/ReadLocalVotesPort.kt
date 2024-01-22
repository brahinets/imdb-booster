package io.ysb.imdb.booster.port.output

import java.io.Reader

fun interface ReadLocalVotesPort {
    fun readLocalTitles(reader: Reader): List<KpLocalTitle>
}
