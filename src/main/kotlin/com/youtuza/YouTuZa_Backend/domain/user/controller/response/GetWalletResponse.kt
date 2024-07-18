package com.youtuza.YouTuZa_Backend.domain.user.controller.response

data class GetWalletResponse(
    val coin: Int,
    val haveStockList: List<HaveStock>
)

data class HaveStock(
    val imageUrl: String,
    val stockName: String,
    val currentStockPrice: Int,
    val haveCount: Int,
    val purchase: Int,
    val rateOfReturn: Double,
)
