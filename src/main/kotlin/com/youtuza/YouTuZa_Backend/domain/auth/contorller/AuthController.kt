package com.youtuza.YouTuZa_Backend.domain.auth.contorller

import com.youtuza.YouTuZa_Backend.domain.auth.contorller.dto.request.LoginRequest
import com.youtuza.YouTuZa_Backend.domain.auth.contorller.dto.request.SignupRequest
import com.youtuza.YouTuZa_Backend.domain.auth.service.LoginService
import com.youtuza.YouTuZa_Backend.domain.auth.service.SignupService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val loginService: LoginService,
    private val signupService: SignupService
) {
    @PostMapping("/login")
    fun login(
        @RequestBody
        loginRequest: LoginRequest
    ) = loginService.login(loginRequest)

    @PostMapping("/signup")
    fun signup(
        @RequestBody
        signupRequest: SignupRequest
    ) = signupService.signup(signupRequest)
}