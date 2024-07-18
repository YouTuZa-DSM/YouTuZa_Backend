package com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.response

data class GetYoutuberResponse(
    val youtuberName: String,
    val price: Int,
    val profileImageUrl: String,
    val priceChangeRate: Double
)
