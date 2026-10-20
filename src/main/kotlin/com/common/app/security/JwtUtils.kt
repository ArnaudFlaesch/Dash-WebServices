package com.common.app.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.Date

@Component
class JwtUtils(
    @Value("\${dash.app.jwtSecret}")
    jwtSecret: String,
    @Value("\${dash.app.jwtExpirationMs}")
    jwtExpirationMs: Long
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    private val jwtExpiration = Duration.ofMillis(jwtExpirationMs)
    private val parser = Jwts.parser().verifyWith(signingKey).build()

    fun generateJwtToken(username: String): String {
        val now = Instant.now()
        return Jwts
            .builder()
            .subject(username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now + jwtExpiration))
            .signWith(signingKey)
            .compact()
    }

    /** Returns the token's subject, or null if the token is malformed, expired or wrongly signed. */
    fun getUserNameFromValidToken(token: String): String? =
        try {
            parser.parseSignedClaims(token).payload.subject
        } catch (e: JwtException) {
            logger.error("Invalid JWT token: {}", e.message)
            null
        }
}
