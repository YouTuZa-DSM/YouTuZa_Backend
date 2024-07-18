package com.youtuza.YouTuZa_Backend.domain.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.youtuza.YouTuZa_Backend.domain.auth.contorller.dto.request.LoginRequest
import com.youtuza.YouTuZa_Backend.domain.user.repository.UserRepository
import com.youtuza.YouTuZa_Backend.infrastructure.security.jwt.JwtAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import xquare.app.xquareinfra.domain.auth.adapter.dto.response.TokenResponse
import com.youtuza.YouTuZa_Backend.infrastructure.exception.BusinessLogicException
import com.youtuza.YouTuZa_Backend.infrastructure.exception.YouTuZaException

@Transactional
@Service
class LoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtAdapter: JwtAdapter
) {
    fun login(loginRequest: LoginRequest): TokenResponse {
        val user = userRepository.findByAccountId(loginRequest.accountId) ?: throw BusinessLogicException.USER_NOT_FOUND

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw YouTuZaException.FORBIDDEN
        }

        val tokenPair = jwtAdapter.generateTokens(user.id.toString())
        return TokenResponse(
            accessToken = tokenPair.first,
            refreshToken = tokenPair.second,
        )
    }
}
