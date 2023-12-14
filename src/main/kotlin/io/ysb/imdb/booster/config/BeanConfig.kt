package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.MovieService
import io.ysb.imdb.booster.domain.RatingService
import io.ysb.imdb.booster.domain.port.output.GetMovieScorePort
import io.ysb.imdb.booster.domain.port.output.SearchMovieByIdPort
import io.ysb.imdb.booster.domain.port.output.SetMovieRatingPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun getMovieService(
        getMovieScorePort: GetMovieScorePort,
        searchMovieByIdPort: SearchMovieByIdPort
    ): MovieService {
        return MovieService(getMovieScorePort, searchMovieByIdPort)
    }

    @Bean
    fun ratingService(
        setMovieRatingPort: SetMovieRatingPort,
    ): RatingService {
        return RatingService(setMovieRatingPort)
    }
}