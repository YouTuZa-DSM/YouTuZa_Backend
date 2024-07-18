package com.youtuza.YouTuZa_Backend.domain.auth.contorller.dto.request

data class SignupRequest(
    val accountId: String,
    val password: String,
    val name: String
)
