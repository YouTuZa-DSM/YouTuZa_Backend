package com.youtuza.YouTuZa_Backend.infrastructure.global.config.scan

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationPropertiesScan(basePackages = ["com.youtuza.YouTuZa_Backend"])
@Configuration
class PropertiesScanConfig
