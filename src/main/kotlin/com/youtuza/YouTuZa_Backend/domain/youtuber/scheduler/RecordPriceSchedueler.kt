package com.youtuza.YouTuZa_Backend.domain.youtuber.scheduler

import com.youtuza.YouTuZa_Backend.domain.youtuber.repository.YoutuberRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RecordPriceScheduler(
    private val youtuberRepository: YoutuberRepository
) {
    @Scheduled(fixedRate = 1000) // 1분마다 실행 (60000 밀리초)
    @Transactional
    fun recordPrices() {
        val youtubers = youtuberRepository.findAll()
        youtubers.forEach { youtuber ->
            youtuber.recordPrice()
            youtuberRepository.save(youtuber)
        }
    }
}