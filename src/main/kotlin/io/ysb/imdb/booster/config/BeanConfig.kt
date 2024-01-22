package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.dump.handler.imdb.NonConfidentImdbBatchLoadingService
import io.ysb.imdb.booster.domain.dump.handler.kp.KinoposhukBatchLoadingService
import io.ysb.imdb.booster.domain.rating.MatchingService
import io.ysb.imdb.booster.domain.rating.TitleLoadingService
import io.ysb.imdb.booster.domain.rating.RatingService
import io.ysb.imdb.booster.domain.rating.TitleInfoService
import io.ysb.imdb.booster.filesystem.ReadLocalRatingsAdapter
import io.ysb.imdb.booster.filesystem.ReadLocalVotesAdapter
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.GetTitleInfoUseCase
import io.ysb.imdb.booster.port.input.TitleLoadingUseCase
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.ReadLocalRatingsPort
import io.ysb.imdb.booster.port.output.ReadLocalVotesPort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.port.output.SearchTitlePort
import io.ysb.imdb.booster.port.output.SetTitleRatingPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun titleInfoService(
        getTitleRatingPort: GetTitleRatingPort,
        searchTitleByIdPort: SearchTitleByIdPort
    ): TitleInfoService {
        return TitleInfoService(getTitleRatingPort, searchTitleByIdPort)
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
    fun titleLoadingService(
        getTitleInfoUseCase: GetTitleInfoUseCase,
        rateTitleUseCase: RateTitleUseCase
    ): TitleLoadingService {
        return TitleLoadingService(
            getTitleInfoUseCase,
            rateTitleUseCase
        )
    }

    @Bean
    fun readLocalRatingsAdapter(): ReadLocalRatingsAdapter {
        return ReadLocalRatingsAdapter()
    }

    @Bean
    fun readLocalVotesAdapter(): ReadLocalVotesAdapter {
        return ReadLocalVotesAdapter()
    }

    @Bean
    fun nonConfidentImdbBatchLoadingService(
        titleLoadingUseCase: TitleLoadingUseCase,
        readLocalRatingsPort: ReadLocalRatingsPort
    ): BatchLoadRatingUseCase {
        return NonConfidentImdbBatchLoadingService(
            titleLoadingUseCase,
            readLocalRatingsPort
        )
    }

    @Bean
    fun kinoposhukBatchLoadingService(
        searchTitlePort: SearchTitlePort,
        loadingService: TitleLoadingUseCase,
        titleInfoService: TitleInfoService,
        readLocalRatingsPort: ReadLocalVotesPort
    ): KinoposhukBatchLoadingService {
        return KinoposhukBatchLoadingService(
            searchTitlePort,
            loadingService,
            titleInfoService,
            readLocalRatingsPort
        )
    }
}
