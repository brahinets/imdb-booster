package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.dump.handler.imdb.ImdbBatchLoadingService
import io.ysb.imdb.booster.domain.dump.handler.kp.KinoposhukBatchLoadingService
import io.ysb.imdb.booster.domain.rating.MatchingService
import io.ysb.imdb.booster.domain.rating.RatingLoadingService
import io.ysb.imdb.booster.domain.rating.RatingService
import io.ysb.imdb.booster.domain.rating.TitleService
import io.ysb.imdb.booster.filesystem.LoadLocalRatingsAdapter
import io.ysb.imdb.booster.filesystem.LoadLocalVotesAdapter
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.LoadLocalRatingsPort
import io.ysb.imdb.booster.port.output.LoadLocalVotesPort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.port.output.SearchTitlePort
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
    fun ratingLoadingService(
        getTitleUseCase: GetTitleUseCase,
        rateTitleUseCase: RateTitleUseCase
    ): RatingLoadingService {
        return RatingLoadingService(
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
        return ImdbBatchLoadingService(
            loadRatingUseCase,
            loadLocalRatingsPort
        )
    }

    @Bean
    fun kinoposhukBatchLoadingService(
        searchTitlePort: SearchTitlePort,
        loadingService: LoadRatingUseCase,
        titleService: TitleService,
        loadLocalRatingsPort: LoadLocalVotesPort
    ): KinoposhukBatchLoadingService {
        return KinoposhukBatchLoadingService(
            searchTitlePort,
            loadingService,
            titleService,
            loadLocalRatingsPort
        )
    }
}
