package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.dump.handler.imdb.LoadingService
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.TitleType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LoadingServiceTest {

    private val loadedTitles: List<TitleId> = mutableListOf()

    @Test
    fun `should rate movies`() {
        val loadingService = LoadingService { title -> loadedTitles.addFirst(title.id) }

        val title = LoadingTitle("tt12345678", "Movie-Movie", 2020, 10, TitleType.MOVIE)
        loadingService.loadRating(title)

        assertEquals(listOf("tt12345678"), loadedTitles)
    }

    @Test
    fun `should not rate unsupported titles types`() {
        val loadingService = LoadingService { _ -> throw IllegalStateException("Should not be called") }

        val title = LoadingTitle("tt87654321", "Movie Movie 2", 2020, 10, TitleType.VIDEO)
        loadingService.loadRating(title)

        assertTrue(loadedTitles.isEmpty())
    }
}
