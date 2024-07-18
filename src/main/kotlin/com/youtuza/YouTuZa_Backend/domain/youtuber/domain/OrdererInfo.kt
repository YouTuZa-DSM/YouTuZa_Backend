package com.youtuza.YouTuZa_Backend.domain.youtuber.domain

import com.youtuza.YouTuZa_Backend.domain.BaseUUIDEntity
import com.youtuza.YouTuZa_Backend.domain.user.domain.User
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class OrdererInfo(
    id: UUID? = null,
    orderer: User?,
    orderCount: Int
): BaseUUIDEntity(id) {
    @OneToOne
    var orderer: User? = orderer
        protected set

    var orderCount: Int = orderCount
        protected set

    fun minusOrderCount(orderCount: Int) {
        this.orderCount -= orderCount
    }
}