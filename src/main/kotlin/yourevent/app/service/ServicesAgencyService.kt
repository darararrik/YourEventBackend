package yourevent.app.service

import yourevent.app.dto.ServiceAgencyDto

interface ServicesAgencyService {

    fun getByAgencyId(id: Int): List<ServiceAgencyDto>
    fun getAll(): List<ServiceAgencyDto>
}