package com.youtuza.YouTuZa_Backend.domain.stock.service

import com.youtuza.YouTuZa_Backend.domain.stock.domain.StockInfo
import com.youtuza.YouTuZa_Backend.domain.stock.repository.StockRepository
import com.youtuza.YouTuZa_Backend.domain.user.repository.UserRepository
import com.youtuza.YouTuZa_Backend.domain.youtuber.repository.YoutuberRepository
import com.youtuza.YouTuZa_Backend.infrastructure.auth.ReadCurrentUserAdapter
import com.youtuza.YouTuZa_Backend.infrastructure.exception.YouTuZaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class StockService(
    private val readCurrentUserAdapter: ReadCurrentUserAdapter,
    private val stockRepository: StockRepository,
    private val youtuberRepository: YoutuberRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun buyStock(youtubeName: String, purchasesCount: Int, price: Int) {
        val user = readCurrentUserAdapter.readCurrentUser()
        val youtuber = youtuberRepository.findByName(youtubeName) ?: throw YouTuZaException.NOT_FOUND
        if(user.coin < (youtuber.currentPrice * purchasesCount)) {
            throw YouTuZaException.BAD_REQUEST
        }
        val stock: StockInfo = stockRepository.findByBuyerIdAndYoutubeName(user.id!!, youtubeName) ?:
            StockInfo(
                id = null,
                buyerId = user.id,
                youtubeName = youtubeName,
                purchasesCount = 0,
                totalPrice = 0
            )
        youtuber.buy(purchasesCount, price, user, stock, stockRepository, userRepository, youtuberRepository)
        stockRepository.save(stock)
        userRepository.save(user)
        youtuberRepository.save(youtuber)
    }

    @Transactional
    fun sellStock(youtubeName: String, sellCount: Int, price: Int) {
        val user = readCurrentUserAdapter.readCurrentUser()
        val youtuber = youtuberRepository.findByName(youtubeName) ?: throw YouTuZaException.NOT_FOUND
        val stock: StockInfo = stockRepository.findByBuyerIdAndYoutubeName(user.id!!, youtubeName) ?:
            throw YouTuZaException.BAD_REQUEST
        if(stock.purchasesCount < sellCount) {
            throw YouTuZaException.BAD_REQUEST
        }
        youtuber.sell(sellCount, price, user, stock, stockRepository, userRepository, youtuberRepository)
        stockRepository.save(stock)
        userRepository.save(user)
        youtuberRepository.save(youtuber)
    }
}