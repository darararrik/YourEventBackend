package yourevent.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

//Конфигурирует доступ к API-эндпоинтам, задает фильтр JWT,
// указывает, какие URL доступны без аутентификации, и управляет сессиями.
@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/yourevent/auth/login",
                        "/yourevent/auth/register",
                        "/yourevent/auth/refresh",
                        "/error",
                        "/yourevent/agencies/all/services",
                        "/yourevent/agencies/{agencyId}/services"
                    ).permitAll()  // Эндпоинты, доступные без аутентификации
                    .requestMatchers(HttpMethod.POST, "/yourevent/user").permitAll()  // Регистрация доступна всем
                    .requestMatchers("/yourevent/users**").hasRole("ADMIN")  // Доступ только для ролей ADMIN
                    .requestMatchers("/yourevent/user/me")
                    .authenticated()  // Эндпоинт /me доступен только авторизованным пользователям
                    .anyRequest().fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}