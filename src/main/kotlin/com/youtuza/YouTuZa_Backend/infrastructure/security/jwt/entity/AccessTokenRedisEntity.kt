package com.youtuza.YouTuZa_Backend.infrastructure.security.jwt.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "access_token")
class AccessTokenRedisEntity(
    subject: String,
    accessToken: String,
    ttl: Long
) {
    @Id
    var subject: String = subject
        protected set

    @Indexed
    var accessToken: String = accessToken
        protected set

    @TimeToLive
    var ttl: Long = ttl
        protected set
}
