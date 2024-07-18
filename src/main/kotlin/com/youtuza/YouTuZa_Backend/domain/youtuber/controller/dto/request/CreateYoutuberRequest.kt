package com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.request

import java.time.LocalDateTime

data class CreateYoutuberRequest(
    val youtuberName: String,
    val profileImageUrl: String,
    val youtubeCreatedTime: LocalDateTime,
    val viewAverage: Int,
    val videoCount: Int,
    val category: String,
    val subscribeCount: Int
)
