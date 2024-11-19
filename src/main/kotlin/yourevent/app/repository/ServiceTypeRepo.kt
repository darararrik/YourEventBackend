package yourevent.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.ServiceTypeEntity

interface ServiceTypeRepo : JpaRepository<ServiceTypeEntity, Int> {
}