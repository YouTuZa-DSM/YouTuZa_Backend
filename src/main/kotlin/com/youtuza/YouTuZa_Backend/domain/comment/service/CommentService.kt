package com.youtuza.YouTuZa_Backend.domain.comment.service

import com.youtuza.YouTuZa_Backend.domain.comment.controller.dto.response.CreateCommentRequest
import com.youtuza.YouTuZa_Backend.domain.comment.controller.dto.response.GetCommentResponse
import com.youtuza.YouTuZa_Backend.domain.comment.domain.Comment
import com.youtuza.YouTuZa_Backend.domain.comment.repository.CommentRepository
import com.youtuza.YouTuZa_Backend.infrastructure.auth.ReadCurrentUserAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val readCurrentUserAdapter: ReadCurrentUserAdapter
) {
    fun createComment(youtubeName: String, createCommentRequest: CreateCommentRequest) {
        val user = readCurrentUserAdapter.readCurrentUser()
        val comment = Comment(
            null,
            youtubeName,
            createCommentRequest.title,
            createCommentRequest.content,
            writer = user.name
        )
        commentRepository.save(comment)
    }

    fun getCommentByYoutubeName(youtubeName: String): List<GetCommentResponse> {
        val comments = commentRepository.findAllByYoutubeName(youtubeName)

        return comments.map {
            GetCommentResponse(
                title =  it.title,
                content = it.content,
                name = it.writer
            )
        }.toList()
    }
}