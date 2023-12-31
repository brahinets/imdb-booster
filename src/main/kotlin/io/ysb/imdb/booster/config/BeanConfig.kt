package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.dump.handler.imdb.BatchLoadingService
import io.ysb.imdb.booster.domain.dump.handler.imdb.LoadingService
import io.ysb.imdb.booster.domain.rating.MatchingService
import io.ysb.imdb.booster.domain.rating.RatingService
import io.ysb.imdb.booster.domain.rating.TitleService
import io.ysb.imdb.booster.domain.dump.handler.imdb.MovieHandler
import io.ysb.imdb.booster.domain.dump.handler.imdb.TitleHandler
import io.ysb.imdb.booster.domain.rating.MovieLoader
import io.ysb.imdb.booster.filesystem.LoadLocalRatingsAdapter
import io.ysb.imdb.booster.filesystem.LoadLocalVotesAdapter
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
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
        movieHandler: TitleHandler,
    ): LoadingService {
        return LoadingService(movieHandler)
    }

    @Bean
    fun movieHandler(
        movieLoader: MovieLoader
    ): MovieHandler {
        return MovieHandler(movieLoader)
    }

    @Bean
    fun movieLoader(
        getTitleUseCase: GetTitleUseCase,
        rateTitleUseCase: RateTitleUseCase
    ): MovieLoader {
        return MovieLoader(
            getTitleUseCase,
            rateTitleUseCase
        )
    }

    @Bean
    fun loadLocalRatingsAdapter(): LoadLocalRatingsAdapter {
        return LoadLocalRatingsAdapter()
    }

    @Bean
    fun loadLocalVotesAdapter(): LoadLocalVotesAdapter {
        return LoadLocalVotesAdapter()
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
