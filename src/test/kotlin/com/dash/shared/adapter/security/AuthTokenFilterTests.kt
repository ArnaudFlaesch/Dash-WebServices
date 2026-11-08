package com.dash.shared.adapter.security

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService

class AuthTokenFilterTests {
    private val jwtTokenAdapter: JwtTokenAdapter = mock()
    private val userDetailsService: UserDetailsService = mock()
    private val authTokenFilter = AuthTokenFilter(jwtTokenAdapter, userDetailsService)

    @AfterEach
    fun clearSecurityContext() {
        SecurityContextHolder.clearContext()
    }

    private fun filter(authorizationHeader: String?): MockFilterChain {
        val request = MockHttpServletRequest()
        authorizationHeader?.let { request.addHeader(HttpHeaders.AUTHORIZATION, it) }
        val filterChain = MockFilterChain()
        authTokenFilter.doFilter(request, MockHttpServletResponse(), filterChain)
        return filterChain
    }

    @Test
    fun shouldAuthenticateUserWithValidToken() {
        val userDetails =
            User
                .withUsername("usertest")
                .password("password")
                .roles("USER")
                .build()
        whenever(jwtTokenAdapter.getUserNameFromValidToken("validToken")).thenReturn("usertest")
        whenever(userDetailsService.loadUserByUsername("usertest")).thenReturn(userDetails)

        val filterChain = filter("Bearer validToken")

        val authentication = SecurityContextHolder.getContext().authentication
        assertNotNull(authentication)
        assertEquals(userDetails, authentication?.principal)
        assertNotNull(authentication?.details)
        assertNotNull(filterChain.request)
    }

    @Test
    fun shouldNotAuthenticateWithoutAuthorizationHeader() {
        val filterChain = filter(null)

        assertNull(SecurityContextHolder.getContext().authentication)
        assertNotNull(filterChain.request)
        verify(jwtTokenAdapter, never()).getUserNameFromValidToken(any())
    }

    @Test
    fun shouldNotAuthenticateWithNonBearerAuthorizationHeader() {
        val filterChain = filter("Basic dXNlcjpwYXNzd29yZA==")

        assertNull(SecurityContextHolder.getContext().authentication)
        assertNotNull(filterChain.request)
        verify(jwtTokenAdapter, never()).getUserNameFromValidToken(any())
    }

    @Test
    fun shouldNotAuthenticateWithBlankBearerToken() {
        val filterChain = filter("Bearer    ")

        assertNull(SecurityContextHolder.getContext().authentication)
        assertNotNull(filterChain.request)
        verify(jwtTokenAdapter, never()).getUserNameFromValidToken(any())
    }

    @Test
    fun shouldNotAuthenticateWithInvalidToken() {
        whenever(jwtTokenAdapter.getUserNameFromValidToken("invalidToken")).thenReturn(null)

        val filterChain = filter("Bearer invalidToken")

        assertNull(SecurityContextHolder.getContext().authentication)
        assertNotNull(filterChain.request)
        verify(userDetailsService, never()).loadUserByUsername(any())
    }

    @Test
    fun shouldNotAuthenticateWhenUserDetailsServiceReturnsNull() {
        whenever(jwtTokenAdapter.getUserNameFromValidToken("validToken")).thenReturn("usertest")
        whenever(userDetailsService.loadUserByUsername("usertest")).thenReturn(null)

        val filterChain = filter("Bearer validToken")

        assertNull(SecurityContextHolder.getContext().authentication)
        assertNotNull(filterChain.request)
    }
}
