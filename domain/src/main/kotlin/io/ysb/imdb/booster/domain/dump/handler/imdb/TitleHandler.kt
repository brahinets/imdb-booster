package io.ysb.imdb.booster.domain.dump.handler.imdb

import io.ysb.imdb.booster.port.input.LoadingTitle

fun interface TitleHandler {
    fun handle(title: LoadingTitle)
}
