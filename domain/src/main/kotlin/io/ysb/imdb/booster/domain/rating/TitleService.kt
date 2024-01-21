package io.ysb.imdb.booster.domain.rating

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.Title
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.port.output.TitleSuggestion

class TitleService(
    private val getTitleRatingPort: GetTitleRatingPort,
    private val searchTitleByIdPort: SearchTitleByIdPort
) : GetTitleUseCase {

    override fun getTitle(titleId: TitleId): Title {
        val rating: Int? = getTitleRatingPort.getTitleRating(titleId).orElse(null)
        val info: TitleSuggestion = searchTitleByIdPort.searchTitleById(titleId)

        return Title(
            info.id,
            info.type,
            info.title,
            info.year,
            rating
        )
    }
}
