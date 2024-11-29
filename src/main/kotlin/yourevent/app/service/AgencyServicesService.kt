package yourevent.app.service

import yourevent.app.dto.AgencyServiceDto

interface AgencyServicesService {

    fun getByAgencyId(id: Int): List<AgencyServiceDto>
    fun getAll(): List<AgencyServiceDto>
}