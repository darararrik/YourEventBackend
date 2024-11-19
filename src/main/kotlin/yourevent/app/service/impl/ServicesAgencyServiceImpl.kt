package yourevent.app.service.impl

import org.springframework.stereotype.Service
import yourevent.app.dto.ServiceAgencyDto
import yourevent.app.repository.ServicesAgencyRepo
import yourevent.app.service.ServicesAgencyService
@Service
class ServicesAgencyServiceImpl(private val agencyServiceRepo: ServicesAgencyRepo) : ServicesAgencyService {
    override fun getByAgencyId(id: Int): List<ServiceAgencyDto> {
        return agencyServiceRepo.findByAgencyId(id).map { it.toDto() }
    }

    override fun getAll(): List<ServiceAgencyDto> {
        return agencyServiceRepo.findByOrderByAgencyId().map { it.toDto() }
    }
}