package yourevent.app.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import yourevent.app.dto.agencyDto.AddressDto
import yourevent.app.dto.agencyDto.AgencyContactInfoDto
import yourevent.app.dto.agencyDto.AgencyDto
import yourevent.app.entity.AgencyServiceEntity
import yourevent.app.entity.agency.AddressEntity
import yourevent.app.entity.agency.AgencyContactInfoEntity
import yourevent.app.entity.agency.AgencyEntity
import yourevent.app.exception.AgencyNotFoundException
import yourevent.app.repository.AgencyRepo
import yourevent.app.repository.AgencyServicesRepo
import yourevent.app.repository.ServiceRepo
import yourevent.app.service.AgencyService
import java.math.BigDecimal

@Service
class AgencyServiceImpl(
    private val agencyRepo: AgencyRepo,
    private val agencyServiceRepo: AgencyServicesRepo,
    private val serviceRepo: ServiceRepo
) : AgencyService {
    override fun getAll(pageIndex: Int): List<AgencyDto> =
        agencyRepo.findByOrderByName(PageRequest.of(pageIndex, 20)).map { it.toDto() }

    override fun getByID(id: Int): AgencyDto =
        agencyRepo.findByIdOrNull(id)?.toDto() ?: throw AgencyNotFoundException(id)

    override fun search(prefix: String): List<AgencyDto> {
        return agencyRepo.findByNameStartsWithIgnoreCaseOrderByName(prefix).map { it.toDto() }
    }


    override fun create(dto: AgencyDto): Int {
        val entity = dto.toEntity()
        return agencyRepo.save(entity).id
    }

    override fun update(id: Int, dto: AgencyDto) {
        val existingAgency = agencyRepo.findByIdOrNull(id) ?: throw AgencyNotFoundException(id)
        existingAgency.name = dto.name
        existingAgency.registrationDate = dto.registrationDate
        existingAgency.verified = dto.verified
        existingAgency.contactInfo.email = dto.contactInfo.email
        existingAgency.contactInfo.phone = dto.contactInfo.phone
        existingAgency.contactInfo.address.city = dto.contactInfo.address.city
        existingAgency.contactInfo.address.street = dto.contactInfo.address.street
        existingAgency.contactInfo.address.building = dto.contactInfo.address.building
        agencyRepo.save(existingAgency)
    }

    override fun delete(id: Int) {
        val existingAgency = agencyRepo.findByIdOrNull(id) ?: throw AgencyNotFoundException(id)
        agencyRepo.deleteById(existingAgency.id)
    }


    override fun addServiceToAgency(agencyId: Int, serviceId: Int, price: BigDecimal) {
        val agency = agencyRepo.findById(agencyId).orElseThrow { Exception("Agency not found") }
        val service = serviceRepo.findById(serviceId).orElseThrow { Exception("Service not found") }

        val agencyService = AgencyServiceEntity(
            agency = agency,
            service = service,
            price = price
        )
        agencyServiceRepo.save(agencyService)
    }

    override fun getAgencyWithServices(agencyId: Int): AgencyDto {
        return agencyRepo.findById(agencyId)
            .orElseThrow { Exception("Agency not found") }
            .toDto()
    }

    private fun AgencyDto.toEntity() = AgencyEntity(
        id = 0,
        name = name,
        registrationDate = registrationDate,
        verified = verified,
        contactInfo = AgencyContactInfoEntity(
            phone = contactInfo.phone,
            email = contactInfo.email,
            address = AddressEntity(
                city = contactInfo.address.city,
                street = contactInfo.address.street,
                building = contactInfo.address.building
            )
        ),
    )

    private fun AgencyEntity.toDto() = AgencyDto(
        id = id,
        name = name,
        registrationDate = registrationDate,
        verified = verified,
        contactInfo = AgencyContactInfoDto(
            phone = contactInfo.phone,
            email = contactInfo.email,
            address = AddressDto(
                city = contactInfo.address.city,
                street = contactInfo.address.street,
                building = contactInfo.address.building
            )
        ),
    )


}
