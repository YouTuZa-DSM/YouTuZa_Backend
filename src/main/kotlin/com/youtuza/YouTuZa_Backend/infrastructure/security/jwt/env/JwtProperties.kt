package com.youtuza.YouTuZa_Backend.infrastructure.security.jwt.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("jwt")
data class JwtProperties(
    val secretKey: String,
    val accessExpiredExp: Long,
    val refreshExpiredExp: Long,
    val header: String,
    val prefix: String
)
