package yourevent.app.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import yourevent.app.dto.auth.AuthenticationResponse
import yourevent.app.dto.user.UserDto
import yourevent.app.exception.EmailAlreadyExistsException
import yourevent.app.exception.InvalidPasswordException
import yourevent.app.exception.UserNotFoundException
import yourevent.app.repository.RefreshTokenRepository
import yourevent.app.repository.UserRepo
import yourevent.app.service.UserService
import yourevent.app.service.jwt.TokenService
import java.sql.Date

@Service
class UserServiceImpl(
    val userRepo: UserRepo,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenService: TokenService,
    private val refreshTokenRepository: RefreshTokenRepository
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


    override fun deleteUser(id: Int) {
        val existingUser = userRepo.findByIdOrNull(id) ?: throw UserNotFoundException(id)
        userRepo.deleteById(existingUser.id)
    }

    override fun updateEmail(email: String, newEmail: String): AuthenticationResponse {
        val user = userRepo.findByEmail(email) ?: throw IllegalArgumentException("User not found")

        // Проверка, что новый email не используется
        if (userRepo.findByEmail(newEmail) != null) {
            throw IllegalArgumentException("Email is already in use")
        }

        // Обновление email
        user.email = newEmail
        userRepo.save(user)

        // Генерация новых токенов
        val userDetails = user.mapToUserDetails()
        val accessToken = tokenService.generate(userDetails, tokenService.getAccessTokenExpiration())
        val refreshToken = tokenService.generate(userDetails, tokenService.getRefreshTokenExpiration())
        refreshTokenRepository.save(refreshToken, userDetails)

        return AuthenticationResponse(
            accessToken = accessToken, refreshToken = refreshToken, userDto = user.toDto()
        )
    }

    override fun updatePassword(email: String, newPassword: String, oldPassword: String): AuthenticationResponse {
        val user = userRepo.findByEmail(email) ?: throw UserNotFoundException("User with email $email not found")
        val userDetails = user.mapToUserDetails()
        println("Stored password: ${user.password}")
        println("Old password matches: ${passwordEncoder.matches(oldPassword, user.password)}")
        if (!passwordEncoder.matches(oldPassword, user.password)) {
            throw InvalidPasswordException("Old password is incorrect")
        }



        user.password = passwordEncoder.encode(newPassword)
        userRepo.save(user)
        val accessToken = tokenService.generate(
            userDetails, tokenService.getAccessTokenExpiration()
        )
        val refreshToken = tokenService.generate(
            userDetails, tokenService.getRefreshTokenExpiration()
        )
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userDto = user.toDto()
        )
    }

    override fun updateName(email: String, firstName: String, surname: String): UserDto {
        val user = userRepo.findByEmail(email) ?: throw UserNotFoundException("User with email $email not found")

        user.name = firstName
        user.surname = surname

        return userRepo.save(user).toDto()
    }


}