package io.ysb.imdb.booster.port.output

import java.io.Reader

fun interface LoadLocalVotesPort {
    fun loadLocalTitles(reader: Reader): List<KpLocalTitle>
}
