package fr.acle.notello.gateway.config

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.ResourceProperties
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.EncodedResourceResolver
import org.springframework.web.servlet.resource.PathResourceResolver


@Configuration
class MvcConfig(val resourceProperties: ResourceProperties) : WebMvcConfigurer {

    val log = LoggerFactory.getLogger(MvcConfig::class.java)

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations(*resourceProperties.staticLocations)
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(EncodedResourceResolver())
                .addResolver(PathResourceResolver())
    }

}