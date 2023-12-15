package io.ysb.imdb.booster.domain.port.output

import io.ysb.imdb.booster.domain.TitleId

interface SearchTitleByIdPort {
    fun searchTitleById(titleId: TitleId): TitleSuggestion
}