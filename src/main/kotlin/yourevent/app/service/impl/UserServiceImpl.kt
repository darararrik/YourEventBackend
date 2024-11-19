package yourevent.app.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import yourevent.app.dto.user.UserDto
import yourevent.app.exception.UserNotFoundException
import yourevent.app.repository.UserRepo
import yourevent.app.service.UserService

@Service
class UserServiceImpl(
    val userRepo: UserRepo, private val passwordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun getAll(pageIndex: Int): List<UserDto> =
        userRepo.findByOrderByName(PageRequest.of(pageIndex, 2)).map { it.toDto() }

    override fun getByID(id: Int): UserDto = userRepo.findByIdOrNull(id)?.toDto() ?: throw UserNotFoundException(id)

    override fun searchUser(prefix: String): List<UserDto> =
        userRepo.findByNameStartsWithIgnoreCaseOrderByName(prefix).map { it.toDto() }

    override fun getByEmail(email: String): UserDto =
        userRepo.findByEmail(email)?.toDto() ?: throw UserNotFoundException(email)


    override fun createUser(dto: UserDto): Int {
        val encryptedPassword = passwordEncoder.encode(dto.password)
        val entity = dto.toEntity(encryptedPassword)
        return userRepo.save(entity).id
    }

    override fun updateUser(id: Int, dto: UserDto) {
        val existingUser = userRepo.findByIdOrNull(id) ?: throw UserNotFoundException(id)

        existingUser.name = dto.name
        existingUser.surname = dto.surname
        existingUser.email = dto.email
        existingUser.city = dto.city
        existingUser.password = dto.password

    }

    override fun deleteUser(id: Int) {
        val existingUser = userRepo.findByIdOrNull(id) ?: throw UserNotFoundException(id)
        userRepo.deleteById(existingUser.id)
    }


}