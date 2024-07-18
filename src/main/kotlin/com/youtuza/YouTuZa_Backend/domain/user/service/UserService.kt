package com.youtuza.YouTuZa_Backend.domain.user.service

import com.youtuza.YouTuZa_Backend.domain.stock.repository.StockRepository
import com.youtuza.YouTuZa_Backend.domain.user.controller.response.GetProfileResponse
import com.youtuza.YouTuZa_Backend.domain.user.controller.response.GetWalletResponse
import com.youtuza.YouTuZa_Backend.domain.user.controller.response.HaveStock
import com.youtuza.YouTuZa_Backend.domain.youtuber.repository.YoutuberRepository
import com.youtuza.YouTuZa_Backend.infrastructure.auth.ReadCurrentUserAdapter
import com.youtuza.YouTuZa_Backend.infrastructure.exception.YouTuZaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(
    private val readCurrentUserAdapter: ReadCurrentUserAdapter,
    private val stockRepository: StockRepository,
    private val youtuberRepository: YoutuberRepository
) {
    fun getUserCoin(): GetProfileResponse {
        val user = readCurrentUserAdapter.readCurrentUser()
        return GetProfileResponse(coin = user.coin, username = user.name)
    }

    fun getWallet(): GetWalletResponse {
        val user = readCurrentUserAdapter.readCurrentUser()
        val stocks = stockRepository.findAllByBuyerId(user.id!!)
        val response = GetWalletResponse(
            coin = user.coin,
            haveStockList = stocks.map {
                val youtube = youtuberRepository.findByName(it.youtubeName) ?: throw YouTuZaException.NOT_FOUND
                HaveStock(
                    imageUrl = youtube.profileImageUrl,
                    stockName = youtube.name,
                    haveCount = it.purchasesCount,
                    purchase = it.totalPrice,
                    rateOfReturn = it.calculateProfitRate(youtube.currentPrice),
                    currentStockPrice = youtube.currentPrice
                )
            }
        )

        return response
    }
}