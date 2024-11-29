package yourevent.app.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.dto.UpdateNameRequest
import yourevent.app.entity.user.UserEntity

interface UserRepo : JpaRepository<UserEntity, Int> {
    fun findByOrderByName(pageable: Pageable): List<UserEntity>
    fun findByNameStartsWithIgnoreCaseOrderByName(prefix: String): List<UserEntity>
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}