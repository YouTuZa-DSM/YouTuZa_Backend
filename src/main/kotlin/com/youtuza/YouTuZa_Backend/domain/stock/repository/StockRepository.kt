package com.youtuza.YouTuZa_Backend.domain.stock.repository

import com.youtuza.YouTuZa_Backend.domain.stock.domain.StockInfo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StockRepository : JpaRepository<StockInfo, UUID> {
    fun findByBuyerIdAndYoutubeName(buyerId: UUID, youtubeName: String): StockInfo?
    fun findAllByBuyerId(buyerId: UUID): List<StockInfo>
}