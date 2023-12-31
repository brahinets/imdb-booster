package io.ysb.imdb.booster.port.output

import io.ysb.imdb.booster.domain.TitleId
import java.util.Optional

fun interface GetTitleRatingPort {
    fun getTitleRating(titleId: TitleId): Optional<Int>
}
