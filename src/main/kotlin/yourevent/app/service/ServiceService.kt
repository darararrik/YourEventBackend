package yourevent.app.service

import yourevent.app.dto.ServiceDto
import yourevent.app.dto.agencyDto.AgencyDto

interface ServiceService {
    //fun createService(name: String, serviceTypeId: Int): ServiceDto
    fun getById(id: Int): ServiceDto

}