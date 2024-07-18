package com.youtuza.YouTuZa_Backend.domain.youtuber.repository

import com.youtuza.YouTuZa_Backend.domain.youtuber.domain.Youtuber
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface YoutuberRepository : JpaRepository<Youtuber, UUID>{
    fun findByName(name: String): Youtuber?
}