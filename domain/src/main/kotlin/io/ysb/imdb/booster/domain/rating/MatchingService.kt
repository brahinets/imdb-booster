package io.ysb.imdb.booster.domain.rating

import io.ysb.imdb.booster.port.input.MatchTitleUseCase
import io.ysb.imdb.booster.port.input.MatchingTitle

class MatchingService : MatchTitleUseCase {
    override fun matchTitle(first: MatchingTitle, second: MatchingTitle): Boolean {
        return first.name == second.name && first.year == second.year
    }
}
