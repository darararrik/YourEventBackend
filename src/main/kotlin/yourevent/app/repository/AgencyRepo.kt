package yourevent.app.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.agency.AgencyEntity

interface AgencyRepo : JpaRepository<AgencyEntity, Int> {
    fun findByOrderByName(pageable: Pageable): List<AgencyEntity>
    fun findByNameStartsWithIgnoreCaseOrderByName(prefix: String): List<AgencyEntity>



}