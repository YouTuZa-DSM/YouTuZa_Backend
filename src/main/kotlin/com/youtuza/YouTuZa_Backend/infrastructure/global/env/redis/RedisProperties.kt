package com.youtuza.YouTuZa_Backend.infrastructure.global.env.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "redis")
data class RedisProperties(
    val host: String,
    val port: Int
)
