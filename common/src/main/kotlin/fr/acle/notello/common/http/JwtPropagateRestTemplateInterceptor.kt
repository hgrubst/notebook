package fr.acle.notello.common.http

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

class JwtPropagateRestTemplateInterceptor : ClientHttpRequestInterceptor {
    override fun intercept(req: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        if (SecurityContextHolder.getContext().authentication !is AnonymousAuthenticationToken) {
            val token: Jwt = SecurityContextHolder.getContext().authentication.credentials as Jwt;
            req.headers.add("Authorization", "Bearer ${token.tokenValue}")
        }
        return execution.execute(req, body);
    }
}