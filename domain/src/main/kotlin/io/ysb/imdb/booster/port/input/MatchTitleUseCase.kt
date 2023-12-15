package io.ysb.imdb.booster.port.input

interface MatchTitleUseCase {
    fun matchTitle(first: MatchingTitle, second: MatchingTitle): Boolean
}
