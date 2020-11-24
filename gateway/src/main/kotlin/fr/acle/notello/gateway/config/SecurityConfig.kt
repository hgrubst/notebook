package fr.acle.notello.gateway.config

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.security.StaticResourceLocation
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    val log = LoggerFactory.getLogger(SecurityConfig::class.java)

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests {
                    it
                            //allow static resources (needed in prod only when serving the ui through spring)
                            .antMatchers(HttpMethod.GET, "/").permitAll()
                            .antMatchers(HttpMethod.GET, "/index.html").permitAll()
                            .antMatchers(HttpMethod.GET, "/static/**").permitAll()
                            .requestMatchers(PathRequest.toStaticResources().at(StaticResourceLocation.FAVICON)).permitAll()
                            //all the rest must be authenticated
                            .anyRequest().authenticated()
                }.oauth2ResourceServer().jwt()
        // .authorizeRequests { it.anyRequest().permitAll() }
    }

//    override fun configure(auth: AuthenticationManagerBuilder?) {
//
//        http.authorizeRequests {
//            it.anyRequest().authenticated()
//        }.oauth2ResourceServer().jwt()
//                .antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
//                .hasAuthority("SCOPE_read")
//                .antMatchers(HttpMethod.POST, "/api/foos")
//                .hasAuthority("SCOPE_write")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
//    }
}