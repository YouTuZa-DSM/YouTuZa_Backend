package com.youtuza.YouTuZa_Backend.domain.youtuber.domain

import com.youtuza.YouTuZa_Backend.domain.BaseUUIDEntity
import java.util.*
import javax.persistence.*

@Entity(name = "tbl_ask_price_info")
class OrderInfo(
    id: UUID? = null,
    price: Int,
    ordererList: MutableList<OrdererInfo> = mutableListOf()
) : BaseUUIDEntity(id) {
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "order_info_id")
    var ordererList: MutableList<OrdererInfo> = ordererList

    var price: Int = price
        protected set

    fun getOrderCount(): Int {
        return ordererList.sumOf { it.orderCount }
    }
}