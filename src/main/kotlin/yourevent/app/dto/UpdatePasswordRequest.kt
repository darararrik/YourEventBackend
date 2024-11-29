package yourevent.app.dto



data class UpdatePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)


