package com.youtuza.YouTuZa_Backend.domain.comment.repository

import com.youtuza.YouTuZa_Backend.domain.comment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CommentRepository : JpaRepository<Comment, UUID> {
    fun findAllByYoutubeName(youtubeName: String): List<Comment>
}