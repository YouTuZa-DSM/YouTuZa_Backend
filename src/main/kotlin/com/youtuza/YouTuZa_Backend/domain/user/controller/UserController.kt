package com.youtuza.YouTuZa_Backend.domain.user.controller

import com.youtuza.YouTuZa_Backend.domain.user.controller.response.GetProfileResponse
import com.youtuza.YouTuZa_Backend.domain.user.controller.response.GetWalletResponse
import com.youtuza.YouTuZa_Backend.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController(
    private val userService: UserService
){
    @GetMapping("/profile")
    fun getUserCoin(): GetProfileResponse = userService.getUserCoin()

    @GetMapping("/wallet")
    fun getWallet(): GetWalletResponse = userService.getWallet()
}