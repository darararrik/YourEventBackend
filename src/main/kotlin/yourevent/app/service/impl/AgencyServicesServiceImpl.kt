package yourevent.app.service.impl

import org.springframework.stereotype.Service
import yourevent.app.dto.AgencyServiceDto
import yourevent.app.repository.AgencyServicesRepo
import yourevent.app.service.AgencyServicesService
@Service
class AgencyServicesServiceImpl(private val agencyServiceRepo: AgencyServicesRepo) : AgencyServicesService {
    override fun getByAgencyId(id: Int): List<AgencyServiceDto> {
        return agencyServiceRepo.findByAgencyId(id).map { it.toDto() }
    }

    override fun getAll(): List<AgencyServiceDto> {
        return agencyServiceRepo.findByOrderByAgencyId().map { it.toDto() }
    }
}