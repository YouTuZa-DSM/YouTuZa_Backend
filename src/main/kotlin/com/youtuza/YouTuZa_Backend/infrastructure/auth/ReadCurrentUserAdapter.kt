package com.youtuza.YouTuZa_Backend.infrastructure.auth

import com.youtuza.YouTuZa_Backend.domain.user.domain.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import com.youtuza.YouTuZa_Backend.infrastructure.security.principle.CustomUserDetails

@Component
class ReadCurrentUserAdapter {
    fun readCurrentUser(): User =
        (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).user
}
