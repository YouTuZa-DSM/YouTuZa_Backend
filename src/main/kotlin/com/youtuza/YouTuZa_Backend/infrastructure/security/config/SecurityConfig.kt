package com.youtuza.YouTuZa_Backend.infrastructure.security.config

import com.youtuza.YouTuZa_Backend.infrastructure.error.CustomAccessDeniedHandler
import com.youtuza.YouTuZa_Backend.infrastructure.error.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val requestPermitConfig: RequestPermitConfig,
    private val securityFilterChainConfig: SecurityFilterChainConfig,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .formLogin().disable()
            .csrf().disable()
            .cors().and() // CORS 활성화

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()

            .apply(requestPermitConfig).and()
            .authorizeRequests()

            .and()
            .apply(securityFilterChainConfig)
            .and()
            .build()

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
