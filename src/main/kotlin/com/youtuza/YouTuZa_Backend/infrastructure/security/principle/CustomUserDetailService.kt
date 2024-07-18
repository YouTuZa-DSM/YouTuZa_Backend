package com.youtuza.YouTuZa_Backend.infrastructure.security.principle

import com.youtuza.YouTuZa_Backend.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import com.youtuza.YouTuZa_Backend.infrastructure.exception.BusinessLogicException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(userId: String?): CustomUserDetails {
        val user = userRepository.findByIdOrNull(UUID.fromString(userId!!)) ?: throw BusinessLogicException.USER_NOT_FOUND

        return CustomUserDetails(user)
    }
}
