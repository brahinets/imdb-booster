package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.Title
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoadingServiceTest {
    private val titles: MutableMap<TitleId, Title> = mutableMapOf(
        Pair("tt12345678", Title("tt12345678", "Movie-Movie", 2020, 7)),
        Pair("tt87654321", Title("tt87654321", "Movie-Movie 2", 2022, 7)),
    )

    @Test
    fun `should rate matched title`() {
        val loadingService = LoadingService(
            { _, _ -> true },
            { titleId -> titles[titleId]!! },
            { titleId, rating ->
                val newTitle = titles[titleId]!!.copy(myRating = rating)
                titles[titleId] = newTitle
            }
        )

        val title = LoadingTitle("tt12345678", "Movie-Movie", 2020, 10)
        loadingService.loadRating(title)

        assertEquals(10, titles["tt12345678"]!!.myRating)
    }

    @Test
    fun `should not rate non matched title`() {
        val loadingService = LoadingService(
            { _, _ -> false },
            { titleId -> titles[titleId]!! },
            { _, _ -> throw IllegalStateException("Should not be called") }
        )

        val title = LoadingTitle("tt87654321", "Movie Movie 2", 2020, 10)
        loadingService.loadRating(title)

        assertEquals(7, titles["tt87654321"]!!.myRating)
    }
}
