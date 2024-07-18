package com.youtuza.YouTuZa_Backend.domain.youtuber.controller

import com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.request.CreateYoutuberRequest
import com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.response.GetYoutuberIncreaseResponse
import com.youtuza.YouTuZa_Backend.domain.youtuber.controller.dto.response.GetYoutuberResponse
import com.youtuza.YouTuZa_Backend.domain.youtuber.service.CreateYoutuberService
import com.youtuza.YouTuZa_Backend.domain.youtuber.service.GetYoutuberService
import com.youtuza.YouTuZa_Backend.domain.youtuber.service.AskPriceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/youtuber")
@RestController
class YoutuberController(
    private val createYoutuberService: CreateYoutuberService,
    private val getYoutuberService: GetYoutuberService,
    private val askPriceService: AskPriceService
) {
    @PostMapping
    fun createYoutuber(@RequestBody createYoutuberRequest: CreateYoutuberRequest){
        createYoutuberService.createYoutuber(createYoutuberRequest)
    }

    @GetMapping("/top")
    fun getYoutuberTop(): List<GetYoutuberResponse> = getYoutuberService.getYoutuberTopFive()

    @GetMapping("/increase")
    fun getYoutuberIncrease(): List<GetYoutuberIncreaseResponse> = getYoutuberService.getYoutuberIncrease()

    @GetMapping("/all")
    fun getYoutuberAll(): List<GetYoutuberResponse> = getYoutuberService.getAllYoutuber()

    @GetMapping("/details")
    fun getYoutuberDetails(@RequestParam("youtuber-name") youtuberName: String) = getYoutuberService.getYoutuberDetails(youtuberName)

    @GetMapping("/chart")
    fun getYoutuberPriceHistory(@RequestParam("youtuber-name") youtuberName: String) = getYoutuberService.getYoutuberPriceHistory(youtuberName)

    @PutMapping("/init")
    fun initYoutuberAskPrice() = askPriceService.initAskPrice()

    @GetMapping("/ask-price")
    fun getAskPrice(@RequestParam("youtuber-name") youtuberName: String) = askPriceService.getAskPrice(youtuberName)
}