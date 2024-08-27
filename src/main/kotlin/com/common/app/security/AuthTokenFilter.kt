package com.common.app.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class AuthTokenFilter(
    private val jwtUtils: JwtUtils,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.name)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        parseJwt(request)
            ?.let { jwt ->
                jwtUtils
                    .validateJwtToken(jwt)
                    .let { isValidToken ->
                        if (isValidToken) {
                            jwtUtils
                                .getUserNameFromJwtToken(jwt)
                                .let(userDetailsService::loadUserByUsername)
                                .let { userDetails ->
                                    UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.authorities
                                    )
                                }.let { authentication ->
                                    authentication.details =
                                        WebAuthenticationDetailsSource().buildDetails(request)
                                    SecurityContextHolder.getContext().authentication = authentication
                                }
                        }
                    }
            }
        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        val prefix = "Bearer "
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(prefix)) {
            headerAuth.substring(prefix.length, headerAuth.length)
        } else {
            null
        }
    }
}
