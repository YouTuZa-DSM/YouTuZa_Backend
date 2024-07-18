package com.youtuza.YouTuZa_Backend.domain.comment.controller

import com.youtuza.YouTuZa_Backend.domain.comment.controller.dto.response.CreateCommentRequest
import com.youtuza.YouTuZa_Backend.domain.comment.service.CommentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/comment")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun createComment(@RequestParam("youtuber-name") youtubeName: String, @RequestBody createCommentRequest: CreateCommentRequest) {
        commentService.createComment(youtubeName, createCommentRequest)
    }

    @GetMapping
    fun getComments(@RequestParam("youtuber-name") youtubeName: String) = commentService.getCommentByYoutubeName(youtubeName)
}