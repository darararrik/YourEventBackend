package yourevent.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.ServiceEntity

interface ServiceRepo : JpaRepository<ServiceEntity, Int> {
}