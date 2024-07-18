package com.youtuza.YouTuZa_Backend.domain.youtuber.service

import com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.response.*
import com.youtuza.YouTuZa_Backend.domain.youtuber.repository.YoutuberRepository
import com.youtuza.YouTuZa_Backend.infrastructure.exception.YouTuZaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@Service
class GetYoutuberService(
    private val youtuberRepository: YoutuberRepository
) {
    fun getYoutuberTopFive(): List<GetYoutuberResponse> {
        val youtuberList = youtuberRepository.findAll()

        val top5Youtubers = youtuberList.sortedByDescending { it.currentPrice }.take(6)

        return top5Youtubers.map {
            GetYoutuberResponse(
                youtuberName = it.name,
                price = it.currentPrice,
                profileImageUrl = it.profileImageUrl,
                priceChangeRate = it.calculatePriceChangeRate()
            )
        }
    }

    fun getYoutuberIncrease(): List<GetYoutuberIncreaseResponse> {
        val youtuberList = youtuberRepository.findAll()

        val top5Youtubers = youtuberList
            .sortedByDescending { it.calculatePriceChangeRate() }
            .take(6)

        return top5Youtubers.map {
            GetYoutuberIncreaseResponse(
                youtuberName = it.name,
                price = it.currentPrice,
                profileImageUrl = it.profileImageUrl,
                priceChangeRate = it.calculatePriceChangeRate()
            )
        }
    }

    fun getAllYoutuber(): List<GetYoutuberResponse> {
        val youtuberList = youtuberRepository.findAll()

        val youtubers = youtuberList.sortedByDescending { it.currentPrice }

        return youtubers.map {
            GetYoutuberResponse(
                youtuberName = it.name,
                price = it.currentPrice,
                profileImageUrl = it.profileImageUrl,
                priceChangeRate = it.calculatePriceChangeRate()
            )
        }
    }

    fun getYoutuberDetails(youtuberName: String): GetYoutuberDetailsResponse {
        val youtuber = youtuberRepository.findByName(youtuberName) ?: throw YouTuZaException.NOT_FOUND

        return GetYoutuberDetailsResponse(
            youtubeName = youtuber.name,
            priceInfo = PriceInfo(
                currentPrice = youtuber.currentPrice,
                priceDiff = (youtuber.currentPrice - youtuber.yesterdayPrice),
                priceDiffPercent = youtuber.calculatePriceChangeRate()
            ),
            youtuberInfo = YoutuberInfo(
                youtubeName = youtuberName,
                youtubeCreatedDate = youtuber.youtubeCreatedTime,
                averageViewCount = youtuber.viewAverage,
                videoCount = youtuber.videoCount,
                category = youtuber.category
            ),
            priceHistory = youtuber.getPriceHistorySorted()
        )
    }

    fun getYoutuberPriceHistory(youtuberName: String): Map<LocalDateTime, Int> {
        val youtuber = youtuberRepository.findByName(youtuberName) ?: throw YouTuZaException.NOT_FOUND
        return youtuber.getPriceHistorySorted()
    }
}