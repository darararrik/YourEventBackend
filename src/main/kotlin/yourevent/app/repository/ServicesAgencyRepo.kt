package yourevent.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.ServiceAgencyEntity

interface ServicesAgencyRepo : JpaRepository<ServiceAgencyEntity, Int> {
    fun findByAgencyId(agencyId: Int): List<ServiceAgencyEntity>
    fun findByOrderByAgencyId(): List<ServiceAgencyEntity>
}