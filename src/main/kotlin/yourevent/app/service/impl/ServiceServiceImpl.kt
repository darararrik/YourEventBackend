package yourevent.app.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import yourevent.app.dto.ServiceDto
import yourevent.app.dto.agencyDto.AgencyDto
import yourevent.app.exception.AgencyNotFoundException
import yourevent.app.exception.ServiceNotFoundException
import yourevent.app.repository.ServiceRepo
import yourevent.app.repository.ServiceTypeRepo
import yourevent.app.service.ServiceService

@Service
class ServiceServiceImpl(
    private val serviceTypeRepo: ServiceTypeRepo, private val serviceRepo: ServiceRepo
) : ServiceService {
    //    override fun createService(name: String, serviceTypeId: Int): ServiceDto {
//        val serviceType =
//            serviceTypeRepo.findById(serviceTypeId).orElseThrow { Exception("Service Type not found") }
//        val serviceEntity = ServiceEntity(name = name, serviceType = serviceType)
//        serviceTypeRepo.save(serviceType)
//        return serviceEntity.toDto(serviceEntity)
//    }


    override fun getById(id: Int): ServiceDto =
        serviceRepo.findByIdOrNull(id)?.toDto() ?: throw ServiceNotFoundException(id)


}