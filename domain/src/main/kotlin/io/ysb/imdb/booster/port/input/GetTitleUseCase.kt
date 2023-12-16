package io.ysb.imdb.booster.port.input

import io.ysb.imdb.booster.domain.TitleId

fun interface GetTitleUseCase {
    fun getTitle(titleId: TitleId): Title
}
