package com.youtuza.YouTuZa_Backend.domain.stock.domain

import com.youtuza.YouTuZa_Backend.domain.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class StockInfo(
    id: UUID?,
    buyerId: UUID,
    youtubeName: String,
    purchasesCount: Int,
    totalPrice: Int,
): BaseUUIDEntity(id) {
    @Column(columnDefinition = "BINARY(16)")
    var buyerId: UUID = buyerId
        protected set

    var youtubeName = youtubeName
        protected set

    var purchasesCount = purchasesCount
        protected set

    var totalPrice: Int = totalPrice
        protected set

    fun buyStock(purchasesCount: Int, price: Int) {
        this.purchasesCount += purchasesCount
        this.totalPrice += (purchasesCount * price)
    }

    fun sellStock(sellCount: Int, price: Int) {
        this.purchasesCount -= sellCount
        this.totalPrice -= (sellCount * price)
    }

    fun calculateProfitRate(currentPrice: Int): Double {
        val currentTotalValue = purchasesCount * currentPrice
        return if (totalPrice != 0) {
            String.format("%.2f", ((currentTotalValue - totalPrice) / totalPrice.toDouble()) * 100).toDouble() // 소수점 두 번째 자리에서 반올림
        } else {
            0.0
        }
    }
}