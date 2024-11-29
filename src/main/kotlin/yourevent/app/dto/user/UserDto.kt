package yourevent.app.dto.user

import yourevent.app.entity.user.UserEntity
import java.sql.Timestamp

data class UserDto(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val city: String? = null,
    val password: String,
    val urlAvatar: String?,

    ) {
    fun toEntity(encryptedPassword: String) = UserEntity(
        id = id,
        name = name,
        surname = surname,
        email = email,
        city = city,
        password = encryptedPassword,
    )
}
