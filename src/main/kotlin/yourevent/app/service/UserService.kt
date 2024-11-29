package yourevent.app.service

import yourevent.app.dto.auth.AuthenticationResponse
import yourevent.app.dto.user.UserDto

interface UserService {
    fun getAll(pageIndex: Int): List<UserDto>
    fun getByID(id: Int): UserDto
    fun searchUser(prefix: String): List<UserDto>
    fun getByEmail(email: String): UserDto
    fun createUser(dto: UserDto): Int

    fun deleteUser(id: Int)
    fun updateEmail(email: String, newEmail: String): AuthenticationResponse
    fun updateName(email: String, firstName: String, surname: String): UserDto
    fun updatePassword(email: String, newPassword: String, oldPassword: String): AuthenticationResponse
}