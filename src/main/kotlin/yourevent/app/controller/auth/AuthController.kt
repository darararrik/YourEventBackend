package yourevent.app.controller.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yourevent.app.dto.auth.*
import yourevent.app.dto.user.UserDto
import yourevent.app.service.jwt.AuthenticationService

@RestController
@RequestMapping("/yourevent/auth")
class AuthController(
    private val authenticationService: AuthenticationService
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

        val newAccessToken = authenticationService.refreshAccessToken(refreshToken.refreshToken)
        return if (newAccessToken != null) {
            ResponseEntity.ok(TokenResponse(newAccessToken))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(TokenResponse("Invalid or expired refresh token"))
        }
    }

        private fun String.mapToTokenResponse(): TokenResponse =
        TokenResponse(
            accessToken = this
        )
}