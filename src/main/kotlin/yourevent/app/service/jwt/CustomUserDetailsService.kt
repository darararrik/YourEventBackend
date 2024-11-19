package yourevent.app.service.jwt

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import yourevent.app.entity.user.UserEntity
import yourevent.app.repository.UserRepo

typealias ApplicationUser = UserEntity
//Этот класс отвечает за загрузку данных пользователя по его email.
@Service
class CustomUserDetailsService(
    private val userRepo: UserRepo
) : UserDetailsService {
    //Метод loadUserByUsername находит пользователя в UserRepo
    // по email и преобразует данные в UserDetails, которые затем
    // используются для аутентификации и проверки токенов.
    override fun loadUserByUsername(username: String): UserDetails =
        userRepo.findByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")



}