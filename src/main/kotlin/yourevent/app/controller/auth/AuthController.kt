package yourevent.app.controller.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import yourevent.app.dto.UpdatePasswordRequest
import yourevent.app.dto.auth.*
import yourevent.app.dto.user.UserDto
import yourevent.app.service.UserService
import yourevent.app.service.jwt.AuthenticationService
import yourevent.app.service.jwt.TokenService

@RestController
@RequestMapping("/yourevent/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
    private val tokenService: TokenService
) {


    // Метод для регистрации нового пользователя
    @PostMapping("/register")
    fun registerUser(
        @RequestBody userDto: UserDto
    ): AuthenticationResponse {
        return authenticationService.registerUser(userDto)
    }

    @PostMapping("/login")
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest
    ): AuthenticationResponse =
        authenticationService.authentication(authRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody refreshToken: RefreshTokenRequest): ResponseEntity<TokenResponse> {
        val tokens = authenticationService.refreshAccessToken(refreshToken.refreshToken)
        return if (tokens != null) {
            ResponseEntity.ok(TokenResponse(tokens.first, tokens.second)) // Отправляем оба токена
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenResponse("Invalid or expired refresh token", "Invalid or expired refresh token"))
        }
    }

    private fun String.mapToTokenResponse(): TokenResponse =
        TokenResponse(
            accessToken = this,
            refreshToken = this

        )
}