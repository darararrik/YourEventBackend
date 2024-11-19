package yourevent.app.service

import yourevent.app.dto.user.UserDto

interface UserService {
    fun getAll(pageIndex: Int): List<UserDto>

    fun getByID(id: Int): UserDto

    fun searchUser(prefix: String): List<UserDto>
    fun getByEmail(email: String): UserDto

    fun createUser(dto: UserDto): Int
    fun updateUser(id: Int, dto: UserDto)
    fun deleteUser(id: Int)
}