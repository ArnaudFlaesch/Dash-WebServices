package com.dash.shared.adapter.security

import com.dash.shared.application.port.outbound.AccessTokenPort
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

/** Generates the JWT access tokens returned on login, and validates the ones sent with the requests. */
@Component
class JwtTokenAdapter(
    securityProperties: SecurityProperties
) : AccessTokenPort {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.jwt.secret))
    private val jwtExpiration = securityProperties.jwt.expiration
    private val parser = Jwts.parser().verifyWith(signingKey).build()

    override fun generateToken(username: String): String =
        Instant.now().let { now ->
            Jwts
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
