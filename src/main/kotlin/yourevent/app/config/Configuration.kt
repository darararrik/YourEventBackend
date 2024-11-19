package yourevent.app.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


import yourevent.app.repository.UserRepo
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import yourevent.app.service.jwt.CustomUserDetailsService

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {
    // Сервис для получения данных пользователя
    @Bean//
    fun userDetailsService(userRepo: UserRepo): UserDetailsService =
        CustomUserDetailsService(userRepo)

    // Шифровка пароля
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    // Провайдер аутентификации
    @Bean
    fun authenticationProvider(userRepo: UserRepo): AuthenticationProvider =
        DaoAuthenticationProvider().also {
            it.setUserDetailsService(userDetailsService(userRepo))
            it.setPasswordEncoder(encoder())
        }

    // Менеджер аутентификации
    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}
