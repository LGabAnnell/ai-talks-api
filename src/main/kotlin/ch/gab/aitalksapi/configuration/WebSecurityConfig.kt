package ch.gab.aitalksapi.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun securityConfiguration(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize.anyRequest().permitAll()
            }
            .cors { corsConfigurer ->
                val source =  UrlBasedCorsConfigurationSource()
                val config = CorsConfiguration()
                    .apply {
                        addAllowedHeader("*")
                        addAllowedOriginPattern("*")
                        addAllowedMethod("*")
                    }
                source.registerCorsConfiguration("/**", config)
                corsConfigurer.configurationSource(source)
            }
            .csrf { csrf ->
                csrf
                    .disable()
            }

        return http.build()
    }
}