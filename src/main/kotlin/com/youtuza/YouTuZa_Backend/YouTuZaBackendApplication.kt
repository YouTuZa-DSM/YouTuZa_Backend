package com.youtuza.YouTuZa_Backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication
class YouTuZaBackendApplication

fun main(args: Array<String>) {
	runApplication<YouTuZaBackendApplication>(*args)
}
