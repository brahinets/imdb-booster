package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.GetMovieService
import io.ysb.imdb.booster.domain.port.output.GetMovieScorePort
import io.ysb.imdb.booster.domain.port.output.SearchMovieByIdPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun getMovieService(
        getMovieScorePort: GetMovieScorePort,
        searchMovieByIdPort: SearchMovieByIdPort
    ): GetMovieService {
        return GetMovieService(getMovieScorePort, searchMovieByIdPort)
    }
}