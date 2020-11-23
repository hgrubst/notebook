package fr.acle.notello.common.config

import fr.acle.notello.common.http.JwtPropagateRestTemplateInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate();
        restTemplate.interceptors.add(JwtPropagateRestTemplateInterceptor())
        return restTemplate;
    }
}