package yourevent.app.dto.auth

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String

)
