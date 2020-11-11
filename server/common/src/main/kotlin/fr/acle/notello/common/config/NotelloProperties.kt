package fr.acle.notello.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("notello")
class NotelloProperties() {
    lateinit var endpoints: Map<String, String>


    var accessTokenValiditySeconds: Int = 0
}