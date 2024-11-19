package yourevent.app.config

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import yourevent.app.service.jwt.CustomUserDetailsService
import yourevent.app.service.jwt.TokenService
import yourevent.app.service.jwt.AuthenticationService

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val authenticationService: AuthenticationService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        // Проверяем наличие Bearer токена в заголовке
        if (authHeader?.startsWith("Bearer ") != true) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader.substringAfter("Bearer ")

        try {
            // Проверяем, что токен не истек
            if (tokenService.isExpired(jwtToken)) {
                throw ExpiredJwtException(null, null, "Access token expired.")
            }

            val email = tokenService.extractEmail(jwtToken)
            if (email != null && SecurityContextHolder.getContext().authentication == null) {
                val foundUser = userDetailsService.loadUserByUsername(email)

                if (tokenService.isValid(jwtToken, foundUser)) {
                    updateContext(foundUser, request)
                } else {
                    // Если access токен истек, проверяем refresh токен
                    val refreshToken = getRefreshTokenFromRequest(request)
                    if (refreshToken != null && !tokenService.isExpired(refreshToken)) {
                        val newAccessToken = authenticationService.refreshAccessToken(refreshToken)
                        if (newAccessToken != null) {
                            response.setHeader("Authorization", "Bearer $newAccessToken")
                            updateContext(foundUser, request)
                        } else {
                            throw ExpiredJwtException(null, null, "Refresh token expired.")
                        }
                    } else {
                        throw ExpiredJwtException(null, null, "Both access and refresh tokens expired.")
                    }
                }
            }

            filterChain.doFilter(request, response)

        } catch (e: ExpiredJwtException) {
            // Обработка истекших токенов
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Token expired. Please log in again.")
            response.writer.flush()
        }
    }

    private fun getRefreshTokenFromRequest(request: HttpServletRequest): String? {
        // В этом примере используется параметр запроса; можно использовать и заголовок
        return request.getParameter("refresh_token")
    }

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }
}
