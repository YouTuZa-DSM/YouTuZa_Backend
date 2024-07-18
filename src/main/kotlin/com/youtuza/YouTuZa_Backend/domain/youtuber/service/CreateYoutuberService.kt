package com.youtuza.YouTuZa_Backend.domain.youtuber.service

import com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.request.CreateYoutuberRequest
import com.youtuza.YouTuZa_Backend.domain.youtuber.domain.Youtuber
import com.youtuza.YouTuZa_Backend.domain.youtuber.repository.YoutuberRepository
import com.youtuza.YouTuZa_Backend.infrastructure.auth.ReadCurrentUserAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Transactional
@Service
class CreateYoutuberService(
    private val readCurrentUserAdapter: ReadCurrentUserAdapter,
    private val youtuberRepository: YoutuberRepository
) {
    fun createYoutuber(createYoutuberRequest: CreateYoutuberRequest) {
        val price = Random.nextInt(900, 1001)
        val youtuber = Youtuber(
            id = null,
            name = createYoutuberRequest.youtuberName,
            profileImageUrl = createYoutuberRequest.profileImageUrl,
            youtubeCreatedTime = createYoutuberRequest.youtubeCreatedTime,
            viewAverage = createYoutuberRequest.viewAverage,
            category = createYoutuberRequest.category,
            videoCount = createYoutuberRequest.videoCount,
            currentPrice = price,
            yesterdayPrice = price,
            leftBuyCountToNextPrice = 10,
            previousCount = 10,
            subscribeCount = createYoutuberRequest.subscribeCount
        )

        youtuberRepository.save(youtuber)
    }
}