package yourevent.app.dto.auth

data class RegistrationRequest(
    val email: String,
    val password: String,
    val name: String
)