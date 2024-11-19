package yourevent.app.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.event.EventEntity

interface EventRepo : JpaRepository<EventEntity, Int> {
    fun findByOrderByName(pageable: Pageable): List<EventEntity>
    fun findByNameStartsWithIgnoreCaseOrderByName(prefix: String): List<EventEntity>
    fun findByUserEntityId(userId: Int): List<EventEntity>
}