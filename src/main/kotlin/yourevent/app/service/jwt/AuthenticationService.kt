package yourevent.app.service.jwt

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import yourevent.app.config.JwtProperties
import yourevent.app.dto.auth.AuthenticationRequest
import yourevent.app.dto.auth.AuthenticationResponse
import yourevent.app.dto.auth.RefreshTokenRequest
import yourevent.app.dto.auth.RegistrationRequest
import yourevent.app.dto.user.UserDto
import yourevent.app.repository.RefreshTokenRepository
import yourevent.app.repository.UserRepo
import yourevent.app.service.UserService
import java.util.*

//Реализует логику аутентификации и обновления токенов.
//Создает access-токен и refresh-токен для пользователя.
@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepo: UserRepo,
    private val userService: UserService,
) {
    fun registerUser(userDto: UserDto): AuthenticationResponse {
        // Проверка, существует ли пользователь с таким email
        if (userRepo.findByEmail(userDto.email) != null) {
            throw IllegalArgumentException("User with this email already exists")
        }

        // Создаем пользователя и шифруем пароль
        val userId = userService.createUser(userDto)
        val user = userRepo.findByIdOrNull(userId) ?: throw IllegalArgumentException("User creation failed")
        val userDetails = user.mapToUserDetails()
        // Создание токенов
        val accessToken = createAccessToken(userDetails)
        val refreshToken = createRefreshToken(userDetails)
        refreshTokenRepository.save(refreshToken, userDetails)

        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userDto = user.toDto(),
        )
    }

    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password
            )
        )
        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)

        val userDto = userRepo.findByEmail(authenticationRequest.email)?.toDto()
            ?: throw IllegalArgumentException("User not found") // Проверка наличия пользователя в userRepo.
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)
        refreshTokenRepository.save(refreshToken, user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userDto = userDto,


            )
    }

    fun refreshAccessToken(refreshToken: String): Pair<String, String>? {
        // Проверяем валидность refresh токена
        val extractedEmail = tokenService.extractEmail(refreshToken)
            ?: return null // Если email не удалось извлечь, токен некорректен

        // Проверяем, что токен не истёк
        if (tokenService.isExpired(refreshToken)) {
            return null // Токен просрочен
        }

        // Загружаем данные пользователя
        val currentUserDetails = userDetailsService.loadUserByUsername(extractedEmail)

        // Генерируем новый access и refresh токены
        val newAccessToken = createAccessToken(currentUserDetails)
        val newRefreshToken = createRefreshToken(currentUserDetails)

        return Pair(newAccessToken, newRefreshToken)
    }

    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user, expirationDate = getAccessTokenExpiration()
    )

    private fun createRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user, expirationDate = getRefreshTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}