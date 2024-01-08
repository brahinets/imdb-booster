package io.ysb.imdb.booster.domain.rating

import io.ysb.imdb.booster.port.input.LoadingTitle

fun interface TitleLoader {
    fun loadTitle(title: LoadingTitle)
}
