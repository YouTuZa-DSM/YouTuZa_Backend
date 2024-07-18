package com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.response

data class GetAskPriceResponse(
    val currentPrice: Int,
    val priceList: List<Pair<Int, Int>>
)
