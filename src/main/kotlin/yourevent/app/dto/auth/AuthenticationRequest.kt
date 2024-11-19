package yourevent.app.dto.auth

data class AuthenticationRequest(
    val email: String,
    val password: String,
)