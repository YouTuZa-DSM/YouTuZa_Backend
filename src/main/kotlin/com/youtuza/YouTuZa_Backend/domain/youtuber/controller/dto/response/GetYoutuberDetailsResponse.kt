package com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.response

import java.time.LocalDateTime

data class GetYoutuberDetailsResponse(
    val youtubeName: String,
    val priceInfo: PriceInfo,
    val priceHistory: Map<LocalDateTime, Int>,
    val youtuberInfo: YoutuberInfo
)

data class PriceInfo(
    val currentPrice: Int,
    val priceDiff: Int,
    val priceDiffPercent: Double
)

data class YoutuberInfo(
    val youtubeName: String,
    val youtubeCreatedDate: LocalDateTime,
    val averageViewCount: Int,
    val videoCount: Int,
    val category: String
)