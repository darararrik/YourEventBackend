package yourevent.app.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yourevent.app.dto.ServiceAgencyDto
import yourevent.app.dto.agencyDto.AgencyDto
import yourevent.app.service.AgencyService
import yourevent.app.service.ServicesAgencyService
import java.math.BigDecimal

@RestController
@RequestMapping("yourevent/agencies")
class AgencyController(
    private val agencyService: AgencyService,
    private val servicesAgency: ServicesAgencyService

) {
    //api/events?page=1
    @GetMapping()
    fun getAll(@RequestParam("page") pageIndex: Int): List<AgencyDto> =
        agencyService.getAll(pageIndex)

    @GetMapping("/{id}")
    fun getByID(@PathVariable("id") id: Int): AgencyDto =
        agencyService.getByID(id)

    @GetMapping("/search")
    fun searchAgency(@RequestParam("prefix") prefix: String): List<AgencyDto> =
        agencyService.search(prefix)

    @PostMapping()
    fun create(@RequestBody dto: AgencyDto): Int {
        return agencyService.create(dto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody dto: AgencyDto) {
        agencyService.update(id, dto)

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        agencyService.delete(id)

    }

    @PostMapping("/{id}/services/{serviceId}")
    fun addServiceToAgency(
        @RequestBody price: BigDecimal,
        @PathVariable("id") agencyId: Int,
        @PathVariable("serviceId") serviceId: Int
    ) {
        agencyService.addServiceToAgency(agencyId, serviceId, price)
    }


    @GetMapping("/{agencyId}/services")
    fun getAgencyServices(@PathVariable agencyId: Int): ResponseEntity<List<ServiceAgencyDto>> {
        val services = servicesAgency.getByAgencyId(agencyId)
        return ResponseEntity.ok(services)
    }

    @GetMapping("/all/services")
    fun getAllServices(): ResponseEntity<List<ServiceAgencyDto>> {
        val services = servicesAgency.getAll()
        return ResponseEntity.ok(services)
    }
}