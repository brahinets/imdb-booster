package io.ysb.imdb.booster.domain.port.output

import io.ysb.imdb.booster.domain.TitleId
import java.util.Optional

interface GetTitleRatingPort {
    fun getTitleRating(titleId: TitleId): Optional<Int>
}