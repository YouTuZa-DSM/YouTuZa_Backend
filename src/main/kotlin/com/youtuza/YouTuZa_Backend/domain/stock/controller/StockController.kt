package com.youtuza.YouTuZa_Backend.domain.stock.controller

import com.youtuza.YouTuZa_Backend.domain.stock.service.StockService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/stock")
@RestController
class StockController(private val stockService: StockService) {
    @PostMapping("/buy")
    fun buyStock(
        @RequestParam("youtube-name") youtubeName: String,
        @RequestParam("purchases-count") purchasesCount: Int,
        @RequestParam("price") price: Int
    ) = stockService.buyStock(youtubeName, purchasesCount, price)

    @PostMapping("/sell")
    fun sellStock(
        @RequestParam("youtube-name") youtubeName: String,
        @RequestParam("purchases-count") purchasesCount: Int,
        @RequestParam("price") price: Int
    ) = stockService.sellStock(youtubeName, purchasesCount, price)
}