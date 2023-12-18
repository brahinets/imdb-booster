package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.BatchLoadingService
import io.ysb.imdb.booster.domain.LoadingService
import io.ysb.imdb.booster.domain.MatchingService
import io.ysb.imdb.booster.domain.RatingService
import io.ysb.imdb.booster.domain.TitleService
import io.ysb.imdb.booster.filesystem.LoadLocalRatingsAdapter
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.MatchTitleUseCase
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.LoadLocalRatingsPort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.port.output.SetTitleRatingPort
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
    fun matchingService(): MatchingService {
        return MatchingService()
    }

    @Bean
    fun ratingService(
        setTitleRatingPort: SetTitleRatingPort,
        getTitleRatingPort: GetTitleRatingPort,
    ): RatingService {
        return RatingService(setTitleRatingPort, getTitleRatingPort)
    }

    @Bean
    fun loadingService(
        matchTitleUseCase: MatchTitleUseCase,
        getTitleUseCase: GetTitleUseCase,
        rateTitleUseCase: RateTitleUseCase,
    ): LoadingService {
        return LoadingService(
            matchTitleUseCase,
            getTitleUseCase,
            rateTitleUseCase,
        )
    }

    @Bean
    fun loadLocalRatingsAdapter(): LoadLocalRatingsAdapter {
        return LoadLocalRatingsAdapter()
    }

    @Bean
    fun batchLoadRatingUseCase(
        loadRatingUseCase: LoadRatingUseCase,
        loadLocalRatingsPort: LoadLocalRatingsPort
    ): BatchLoadRatingUseCase {
        return BatchLoadingService(
            loadRatingUseCase,
            loadLocalRatingsPort
        )
    }
}
