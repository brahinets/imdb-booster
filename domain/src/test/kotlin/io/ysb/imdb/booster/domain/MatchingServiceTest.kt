package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.rating.MatchingService
import io.ysb.imdb.booster.port.input.MatchingTitle
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class MatchingServiceTest {

    @Test
    fun `should match when name and year are the same`() {
        val matchingService = MatchingService()
        val first = MatchingTitle("Fight Club", 1999)
        val second = MatchingTitle("Fight Club", 1999)

        assertTrue(matchingService.matchTitle(first, second))
    }

    @Test
    fun `should not match when year is different`() {
        val matchingService = MatchingService()
        val first = MatchingTitle("Fight Club", 1999)
        val second = MatchingTitle("Fight Club", 2000)

        assertFalse(matchingService.matchTitle(first, second))
    }

    @Test
    fun `should not match when name is different`() {
        val matchingService = MatchingService()
        val first = MatchingTitle("Fight Club", 1999)
        val second = MatchingTitle("The Fight Club", 1999)

        assertFalse(matchingService.matchTitle(first, second))
    }
}
