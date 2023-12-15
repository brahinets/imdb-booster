package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.SetTitleRatingPort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional

class RatingServiceTest {
    private var titles: MutableMap<String, Int> = mutableMapOf(
        Pair("tt12345678", 8)
    )

    private lateinit var ratingService: RateTitleUseCase

    @BeforeEach
    fun setUp() {
        ratingService = RatingService(
            TestSetTitleRatingPort(),
            TestGetTitleRatingPort()
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
        assertThrows<IllegalArgumentException>(
            "Rate must be between 1 and 10 (inclusive)"
        ) { ratingService.rateTitle("tt87654321", 0) }
    }

    @Test
    fun `max allowed score is 10`() {
        assertThrows<IllegalArgumentException>(
            "Rate must be between 1 and 10 (inclusive)"
        ) { ratingService.rateTitle("tt87654321", 11) }
    }

    private inner class TestGetTitleRatingPort : GetTitleRatingPort {
        override fun getTitleRating(titleId: TitleId): Optional<Int> {
            return Optional.ofNullable(titles[titleId]);
        }
    }

    private inner class TestSetTitleRatingPort : SetTitleRatingPort {
        override fun setTitleRating(titleId: TitleId, rating: Int) {
            titles[titleId] = rating
        }
    }
}
