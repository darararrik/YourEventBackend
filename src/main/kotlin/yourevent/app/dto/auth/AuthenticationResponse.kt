package yourevent.app.dto.auth

import yourevent.app.dto.user.UserDto

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val userDto: UserDto,
)
