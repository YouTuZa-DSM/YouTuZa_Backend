package com.youtuza.YouTuZa_Backend.domain.auth.service

import com.youtuza.YouTuZa_Backend.domain.auth.contorller.dto.request.SignupRequest
import com.youtuza.YouTuZa_Backend.domain.user.domain.User
import com.youtuza.YouTuZa_Backend.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SignupService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun signup(signupRequest: SignupRequest) {
        val user = User(
            id = null,
            name = signupRequest.name,
            accountId = signupRequest.accountId,
            password = passwordEncoder.encode(signupRequest.password)
        )

        userRepository.save(user)
    }
}