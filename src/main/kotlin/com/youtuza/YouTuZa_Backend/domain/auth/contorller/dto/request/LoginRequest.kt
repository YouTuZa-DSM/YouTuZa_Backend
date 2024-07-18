package com.youtuza.YouTuZa_Backend.domain.auth.contorller.dto.request

data class LoginRequest(
    val accountId: String,
    val password: String
)
