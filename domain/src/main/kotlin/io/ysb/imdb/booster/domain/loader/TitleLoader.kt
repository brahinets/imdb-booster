package io.ysb.imdb.booster.domain.loader

import io.ysb.imdb.booster.port.input.LoadingTitle

fun interface TitleLoader {
    fun loadTitle(title: LoadingTitle)
}
