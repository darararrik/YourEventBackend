package yourevent.app.entity.user

import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails
import yourevent.app.dto.user.UserDto
import yourevent.app.entity.event.EventEntity
import java.sql.Timestamp

@Entity
@Table(name = "users")
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Int,

    @Column(name = "user_name")
    var name: String,

    @Column(name = "user_surname")
    var surname: String,

    @Column(name = "user_registration_date")
    var registrationDate: Timestamp = Timestamp(System.currentTimeMillis()),

    @Column(name = "user_email")
    var email: String,

    @Column(name = "user_city")
    var city: String?,

    @Column(name = "user_password")
    var password: String,

    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val events: List<EventEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)  // Используем строковое значение enum
    @Column(name = "user_role")
    val role: Role = Role.USER
) {
    fun toDto() = UserDto(
        id = id,
        name = name,
        surname = surname,
        email = email,
        city = city,
        password = password,
    )

    fun mapToUserDetails(): UserDetails =
        org.springframework.security.core.userdetails.User.builder()
            .username(this.email)
            .password(this.password)
            .build()
}


