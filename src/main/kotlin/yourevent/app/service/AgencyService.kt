package yourevent.app.service

import yourevent.app.dto.agencyDto.AgencyDto
import yourevent.app.entity.agency.AgencyEntity


interface AgencyService {
    fun getAll(pageIndex: Int) : List<AgencyDto>
    fun getByID(id: Int): AgencyDto
    fun search(prefix: String) : List<AgencyDto>
    fun create(dto: AgencyDto) : Int
    fun update(id: Int, dto: AgencyDto)
    fun delete(id: Int)


    fun addServiceToAgency(agencyId: Int, serviceId: Int)
    fun getAgencyWithServices(agencyId: Int): AgencyDto
}