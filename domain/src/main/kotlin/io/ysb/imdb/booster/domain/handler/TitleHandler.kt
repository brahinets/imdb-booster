package io.ysb.imdb.booster.domain.handler

import io.ysb.imdb.booster.port.input.LoadingTitle

fun interface TitleHandler {
    fun handle(title: LoadingTitle)
}
