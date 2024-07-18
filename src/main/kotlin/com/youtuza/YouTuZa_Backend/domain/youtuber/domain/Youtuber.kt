package com.youtuza.YouTuZa_Backend.domain.youtuber.domain

import com.youtuza.YouTuZa_Backend.domain.BaseUUIDEntity
import com.youtuza.YouTuZa_Backend.domain.stock.domain.StockInfo
import com.youtuza.YouTuZa_Backend.domain.user.domain.User
import com.youtuza.YouTuZa_Backend.domain.stock.repository.StockRepository
import com.youtuza.YouTuZa_Backend.domain.user.repository.UserRepository
import com.youtuza.YouTuZa_Backend.domain.youtuber.repository.YoutuberRepository
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import kotlin.random.Random

@Entity(name = "tbl_youtuber")
class Youtuber(
    id: UUID?,
    name: String,
    subscribeCount: Int,
    profileImageUrl: String,
    youtubeCreatedTime: LocalDateTime,
    viewAverage: Int,
    videoCount: Int,
    category: String,
    currentPrice: Int,
    yesterdayPrice: Int,
    leftBuyCountToNextPrice: Int,
    previousCount: Int,
    priceHistory: MutableMap<LocalDateTime, Int> = mutableMapOf(),
    askPrice: MutableMap<Int, OrderInfo> = mutableMapOf()
): BaseUUIDEntity(id) {
    var name: String = name
        protected set

    var profileImageUrl: String = profileImageUrl
        protected set

    var currentPrice: Int = currentPrice
        protected set

    var yesterdayPrice: Int = yesterdayPrice
        protected set

    var subscribeCount: Int = subscribeCount
        protected set

    var youtubeCreatedTime: LocalDateTime = youtubeCreatedTime
        protected set

    var viewAverage: Int = viewAverage
        protected set

    var videoCount: Int = videoCount
        protected set

    var category: String = category
        protected set

    var leftBuyCountToNextPrice: Int = leftBuyCountToNextPrice
        protected set

    var previousCount: Int = previousCount
        protected set

    @ElementCollection
    var priceHistory: MutableMap<LocalDateTime, Int> = priceHistory
        protected set

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @MapKeyColumn(name = "price")
    var askPrice: MutableMap<Int, OrderInfo> = askPrice
        protected set

    fun initAskPrice() {
        for (i: Int in 0..60) {
            askPrice[currentPrice + (i * 5)] = OrderInfo(null,currentPrice + (i * 5) , mutableListOf(OrdererInfo(null, null,Random.nextInt(3, 6) * Random.nextInt(4, 7) )) )
        }

        for (i: Int in 0..60) {
            askPrice[currentPrice - (i * 5)] = OrderInfo(null,currentPrice + (i * 5) , mutableListOf(OrdererInfo(null, null,Random.nextInt(2, 4) * Random.nextInt(2, 4) )) )
        }
    }

    fun recordPrice() {
        val now = LocalDateTime.now().withNano(0) // 초 단위로 정밀도 조정
        priceHistory[now] = currentPrice
    }

    fun calculatePriceChangeRate(): Double {
        return if (yesterdayPrice != 0) {
            val rate = ((currentPrice - yesterdayPrice) / yesterdayPrice.toDouble()) * 100
            String.format("%.2f", rate).toDouble() // 소수점 두 번째 자리에서 반올림
        } else {
            0.0
        }
    }

    fun getPriceHistorySorted(): Map<LocalDateTime, Int> {
        return priceHistory.toSortedMap()
    }

    fun buy(purchasesCount: Int, price: Int, user: User, stock: StockInfo, stockRepository: StockRepository, userRepository: UserRepository, youtuberRepository: YoutuberRepository) {
        if (price >= currentPrice) {
            val orderInfo = askPrice[price]

            if (orderInfo != null) {
                val availableOrderCount = orderInfo.getOrderCount()
                if (availableOrderCount >= purchasesCount) {
                    // 매수 주문을 모두 체결
                    completeOrder(user, price, purchasesCount, stock, stockRepository, userRepository)
                } else {
                    // 남은 매도 주문을 모두 매수하고 남은 수량에 대해 새로운 매수 주문 걸기
                    completeOrder(user, price, availableOrderCount, stock, stockRepository, userRepository)
                    val remainingCount = purchasesCount - availableOrderCount

                    currentPrice += 5

                    placeBuyOrder(user, price, remainingCount)
                }
            } else {
                // 해당 가격에 매도 주문이 없는 경우 새로운 매수 주문을 걸기
                placeBuyOrder(user, price, purchasesCount)
            }
        } else {
            // 구매하려는 가격이 현재 가격보다 낮거나 같은 경우 새로운 매수 주문을 걸기
            placeBuyOrder(user, price, purchasesCount)
        }
        youtuberRepository.save(this)
    }

    private fun completeOrder(user: User, price: Int, count: Int, stock: StockInfo, stockRepository: StockRepository, userRepository: UserRepository) {
        val orderInfo = askPrice[price]
        var remainingCount = count

        if (orderInfo != null) {
            val iterator = orderInfo.ordererList.iterator()
            while (iterator.hasNext() && remainingCount > 0) {
                var ordererInfo = iterator.next()
                if (ordererInfo.orderCount <= remainingCount) {
                    remainingCount -= ordererInfo.orderCount
                    iterator.remove()
                    ordererInfo.orderer?.increaseCoin(price * ordererInfo.orderCount)
                    user.decreaseCoin(price * ordererInfo.orderCount)
                    stock.buyStock(ordererInfo.orderCount, price)
                } else {
                    stock.buyStock(remainingCount, price)
                    user.decreaseCoin(price * remainingCount)
                    ordererInfo.orderer?.increaseCoin(price * remainingCount)
                    ordererInfo.minusOrderCount(remainingCount)
                    remainingCount = 0
                }
            }
        }
        stockRepository.save(stock)
        userRepository.save(user)
    }

    private fun placeBuyOrder(user: User, price: Int, count: Int) {
        if (askPrice.containsKey(price)) {
            val orderInfo = askPrice[price]
            orderInfo?.ordererList?.add(OrdererInfo(null, user, count))
        } else {
            val orderInfo = OrderInfo(UUID.randomUUID(), price)
            orderInfo.ordererList.add(OrdererInfo(null, user, count))
            askPrice[price] = orderInfo
        }
        user.decreaseCoin(price * count)
    }

    fun sell(salesCount: Int, price: Int, user: User, stock: StockInfo, stockRepository: StockRepository, userRepository: UserRepository, youtuberRepository: YoutuberRepository) {
        if (price < currentPrice) {
            val orderInfo = askPrice[price]

            if (orderInfo != null) {
                val availableOrderCount = orderInfo.getOrderCount()
                if (availableOrderCount >= salesCount) {
                    // 매도 주문을 모두 체결
                    completeSellOrder(user, price, salesCount, stock, stockRepository, userRepository)
                } else {
                    // 남은 매수 주문을 모두 매도하고 남은 수량에 대해 새로운 매도 주문 걸기
                    completeSellOrder(user, price, availableOrderCount, stock, stockRepository, userRepository)
                    val remainingCount = salesCount - availableOrderCount

                    currentPrice -= 5

                    placeSellOrder(user, price, remainingCount, stock)
                }
            } else {
                // 해당 가격에 매수 주문이 없는 경우 새로운 매도 주문을 걸기
                placeSellOrder(user, price, salesCount, stock)
            }
        } else {
            // 판매하려는 가격이 현재 가격보다 높거나 같은 경우 새로운 매도 주문을 걸기
            placeSellOrder(user, price, salesCount, stock)
        }
        youtuberRepository.save(this)
    }

    private fun completeSellOrder(user: User, price: Int, count: Int, stock: StockInfo, stockRepository: StockRepository, userRepository: UserRepository) {
        val orderInfo = askPrice[price]
        var remainingCount = count

        if (orderInfo != null) {
            val iterator = orderInfo.ordererList.iterator()
            while (iterator.hasNext() && remainingCount > 0) {
                var ordererInfo = iterator.next()
                if (ordererInfo.orderCount <= remainingCount) {
                    remainingCount -= ordererInfo.orderCount
                    iterator.remove()
                    user.increaseCoin(price * ordererInfo.orderCount)
                    stock.sellStock(ordererInfo.orderCount, price)
                } else {
                    stock.sellStock(remainingCount, price)
                    user.increaseCoin(price * remainingCount)
                    ordererInfo.minusOrderCount(remainingCount)
                    remainingCount = 0
                }
            }
        }
        stockRepository.save(stock)
        userRepository.save(user)
    }

    private fun placeSellOrder(user: User, price: Int, count: Int, stock: StockInfo) {
        if (askPrice.containsKey(price)) {
            val orderInfo = askPrice[price]
            orderInfo?.ordererList?.add(OrdererInfo(null, user, count))
        } else {
            val orderInfo = OrderInfo(UUID.randomUUID(), price)
            orderInfo.ordererList.add(OrdererInfo(null, user, count))
            askPrice[price] = orderInfo
        }

        stock.sellStock(count, price)
    }

    fun getAskPriceAroundTen(): List<Pair<Int, Int>> {
        // key 기준으로 내림차순 정렬
        val sortedKeys = askPrice.keys.sortedDescending()
        val currentIndex = sortedKeys.indexOf(currentPrice)
        val startIndex = maxOf(0, currentIndex - 10)
        val endIndex = minOf(sortedKeys.size - 1, currentIndex + 10)

        val result = mutableListOf<Pair<Int, Int>>()
        for (i in startIndex..endIndex) {
            val key = sortedKeys[i]
            askPrice[key]?.let {
                result.add(Pair(key, it.getOrderCount()))
            }
        }
        return result.sortedByDescending { it.first }
    }
}
