package yourevent.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.AgencyServiceEntity

interface AgencyServiceRepo : JpaRepository<AgencyServiceEntity, Int> {
}