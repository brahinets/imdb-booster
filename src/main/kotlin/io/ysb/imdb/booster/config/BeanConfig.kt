package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.TitleService
import io.ysb.imdb.booster.domain.RatingService
import io.ysb.imdb.booster.domain.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.domain.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.domain.port.output.SetTitleRatingPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun titleService(
        getTitleRatingPort: GetTitleRatingPort,
        searchTitleByIdPort: SearchTitleByIdPort
    ): TitleService {
        return TitleService(getTitleRatingPort, searchTitleByIdPort)
    }

    @Bean
    fun ratingService(
        setTitleRatingPort: SetTitleRatingPort,
    ): RatingService {
        return RatingService(setTitleRatingPort)
    }
}