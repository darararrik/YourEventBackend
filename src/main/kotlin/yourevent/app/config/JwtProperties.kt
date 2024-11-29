package yourevent.app.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val key: String,
    val accessTokenExpiration: Long, // 5 минут по умолчанию
    val refreshTokenExpiration: Long
)


