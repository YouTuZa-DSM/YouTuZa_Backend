package com.youtuza.YouTuZa_Backend.domain.youtuber.service

import com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.response.GetAskPriceResponse
import com.youtuza.YouTuZa_Backend.domain.youtuber.repository.YoutuberRepository
import com.youtuza.YouTuZa_Backend.infrastructure.exception.YouTuZaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AskPriceService(
    private val youtuberRepository: YoutuberRepository
) {
    fun initAskPrice() {
        val youtuberList = youtuberRepository.findAll()

        youtuberList.forEach {
            it.initAskPrice()
        }
    }

    fun getAskPrice(youtuberName: String): GetAskPriceResponse {
        val youtuber = youtuberRepository.findByName(youtuberName) ?: throw YouTuZaException.NOT_FOUND
        return GetAskPriceResponse(youtuber.currentPrice, youtuber.getAskPriceAroundTen())
    }
}