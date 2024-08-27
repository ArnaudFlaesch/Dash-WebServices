package com.common.app.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils(
    @Value("\${dash.app.jwtSecret}")
    private val jwtSecret: String,
    @Value("\${dash.app.jwtExpirationMs}")
    private val jwtExpirationMs: Int
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl

        return Jwts
            .builder()
            .subject(userPrincipal.username)
            .issuedAt(Date())
            .expiration(Date(Date().time + jwtExpirationMs))
            .signWith(getSigningKey())
            .compact()
    }

    fun getUserNameFromJwtToken(token: String): String = parseToken(token).payload.subject

    fun validateJwtToken(authToken: String): Boolean =
        try {
            parseToken(authToken)
            true
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
            false
        }

    private fun parseToken(authToken: String) =
        Jwts
            .parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(authToken)

    private fun getSigningKey(): SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
}
