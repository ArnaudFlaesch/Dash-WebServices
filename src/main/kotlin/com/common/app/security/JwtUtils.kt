package com.common.app.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils {
    @Value("\${dash.app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${dash.app.jwtExpirationMs}")
    private val jwtExpirationMs = 0
    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl
        return Jwts.builder().setSubject(userPrincipal.username).setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserNameFromJwtToken(token: String): String {
        val bytes = Decoders.BASE64.decode(jwtSecret)
        val key: SecretKey = Keys.hmacShaKeyFor(bytes)
        return Jwts.parser().verifyWith(key).build().parseUnsecuredClaims(token).payload.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            val bytes = Decoders.BASE64.decode(jwtSecret)
            val key: SecretKey = Keys.hmacShaKeyFor(bytes)
            Jwts.parser().verifyWith(key).build().parseUnsecuredClaims(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.name)
    }
}
