package yourevent.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.AgencyServiceEntity

interface AgencyServicesRepo : JpaRepository<AgencyServiceEntity, Int> {
    fun findByAgencyId(agencyId: Int): List<AgencyServiceEntity>
    fun findByOrderByAgencyId(): List<AgencyServiceEntity>
}