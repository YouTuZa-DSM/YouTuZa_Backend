package com.youtuza.YouTuZa_Backend.domain.user.domain

import com.youtuza.YouTuZa_Backend.domain.BaseUUIDEntity
import java.util.*
import javax.persistence.Entity

@Entity
class User (
    id: UUID?,
    name: String,
    accountId: String,
    password: String,
    coin: Int = 1000
): BaseUUIDEntity(id) {
    var name: String = name
        protected set

    var accountId: String = accountId
        protected set

    var password: String = password
        protected set

    var coin: Int = coin
        protected set

    fun decreaseCoin(coin: Int) {
        this.coin -= coin
    }

    fun increaseCoin(coin: Int) {
        this.coin += coin
    }
}