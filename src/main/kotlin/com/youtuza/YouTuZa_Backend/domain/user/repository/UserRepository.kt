package com.youtuza.YouTuZa_Backend.domain.user.repository

import com.youtuza.YouTuZa_Backend.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByAccountId(accountId: String): User?
}