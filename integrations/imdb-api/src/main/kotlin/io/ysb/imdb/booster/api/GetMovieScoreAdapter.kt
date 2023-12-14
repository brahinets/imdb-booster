package io.ysb.imdb.booster.api

import io.ysb.imdb.booster.api.model.Model
import io.ysb.imdb.booster.domain.MovieId
import io.ysb.imdb.booster.domain.port.GetMovieScorePort
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetMovieScoreAdapter(val imdbClient: ApiClient) : GetMovieScorePort {

    override fun getMovieScore(movieId: MovieId): Optional<Int> {
        val get: Model = imdbClient.get(movieId)

        val titles = get.data.titles
        if (titles.isEmpty()) {
            return Optional.empty()
        }

        val value = titles.first().userRating.value

        return Optional.of(value)
    }
}