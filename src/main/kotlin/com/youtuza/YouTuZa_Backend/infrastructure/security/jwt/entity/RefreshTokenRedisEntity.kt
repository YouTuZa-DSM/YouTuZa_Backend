package com.youtuza.YouTuZa_Backend.infrastructure.security.jwt.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refresh_token")
class RefreshTokenRedisEntity(
    subject: String,
    rfToken: String,
    ttl: Long
) {
    @Id
    var subject: String = subject
        protected set

    @Indexed
    var rfToken: String = rfToken
        protected set

    @TimeToLive
    var ttl: Long = ttl
        protected set
}
