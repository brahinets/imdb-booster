package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.port.input.GetTitleUseCase
import io.ysb.imdb.booster.domain.port.input.Title
import io.ysb.imdb.booster.domain.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.domain.port.output.TitleSuggestion
import io.ysb.imdb.booster.domain.port.output.SearchTitleByIdPort

class TitleService(
    private val getTitleRatingPort: GetTitleRatingPort,
    private val searchTitleByIdPort: SearchTitleByIdPort
) : GetTitleUseCase {

    override fun getTitle(titleId: TitleId): Title {
        val rating: Int? = getTitleRatingPort.getTitleRating(titleId).orElse(null)
        val info: TitleSuggestion = searchTitleByIdPort.searchTitleById(titleId)

        return Title(
            info.id,
            info.title,
            info.year,
            rating
        )
    }
}