package io.ysb.imdb.booster.port.input

import io.ysb.imdb.booster.domain.TitleId

fun interface GetTitleInfoUseCase {
    fun getTitle(titleId: TitleId): Title
}
