package io.ysb.imdb.booster.port.input

fun interface MatchTitleUseCase {
    fun matchTitle(first: MatchingTitle, second: MatchingTitle): Boolean
}
