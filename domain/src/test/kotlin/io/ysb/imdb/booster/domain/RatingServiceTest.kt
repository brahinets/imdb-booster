package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.rating.RatingService
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class RatingServiceTest {
    private val titles: MutableMap<String, Int> = mutableMapOf(
        Pair("tt12345678", 8)
    )

    private lateinit var ratingService: RateTitleUseCase

    @BeforeEach
    fun setUp() {
        ratingService = RatingService(
            { titleId, rating -> titles[titleId] = rating },
            { titleId -> Optional.ofNullable(titles[titleId]) }
        )
    }

    @Test
    fun `rate title should rate title without rate yet`() {
        ratingService.rateTitle("tt87654321", 10)

        assertEquals(10, titles["tt87654321"])
    }

    @Test
    fun `rate title should not rate already rated title`() {
        ratingService.rateTitle("tt12345678", 10)

        assertEquals(8, titles["tt12345678"])
    }

    @Test
    fun `min allowed score is 1`() {
        val error = assertThrows<IllegalArgumentException> {
            ratingService.rateTitle("tt87654321", 0)
        }
        assertEquals("Rate must be between 1 and 10 (inclusive)", error.message)
    }

    @Test
    fun `max allowed score is 10`() {
        val error = assertThrows<IllegalArgumentException> {
            ratingService.rateTitle("tt87654321", 11)
        }
        assertEquals("Rate must be between 1 and 10 (inclusive)", error.message)
    }
}
