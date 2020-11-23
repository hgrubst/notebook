package fr.acle.notello.note.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests { it.anyRequest().authenticated() }.oauth2ResourceServer().jwt()
//                .authorizeRequests { it.anyRequest().permitAll() }
    }
}