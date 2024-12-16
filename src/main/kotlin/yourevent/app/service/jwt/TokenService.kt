package yourevent.app.service.jwt

import io.github.cdimascio.dotenv.Dotenv
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import yourevent.app.config.JwtProperties
import java.util.*

//Создает JWT токены, проверяет их валидность и срок действия.
@Service
class TokenService(
    private val jwtProperties: JwtProperties
) {
    // Загрузка ключа из .env файла
    // Загрузка ключа из .env файла
    private val jwtSecretKey: String = Dotenv.load().get("JWT_SECRET_KEY")!!

    // Создаем секретный ключ из строки (Base64)
    private val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecretKey))
    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)

        return userDetails.username == email && !isExpired(token)
    }

    fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject

    fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }

    fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}