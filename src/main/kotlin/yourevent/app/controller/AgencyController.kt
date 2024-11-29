package yourevent.app.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yourevent.app.dto.AgencyServiceDto
import yourevent.app.dto.agencyDto.AgencyDto
import yourevent.app.service.AgencyService
import yourevent.app.service.AgencyServicesService
import java.math.BigDecimal

@RestController
@RequestMapping("yourevent/agencies")
class AgencyController(
    private val agencyService: AgencyService,
    private val servicesAgency: AgencyServicesService

) {
    //получить список всех агенств
    @GetMapping()
    fun getAll(@RequestParam("page") pageIndex: Int): List<AgencyDto> =
        agencyService.getAll(pageIndex)


    //получить агентство по ID
    @GetMapping("/{id}")
    fun getByID(@PathVariable("id") id: Int): AgencyDto =
        agencyService.getByID(id)
    //поиск по имени агентства
    @GetMapping("/search")
    fun searchAgency(@RequestParam("prefix") prefix: String): List<AgencyDto> =
        agencyService.search(prefix)
    //Создать агентство
    //ручка не нужна
    @PostMapping()
    fun create(@RequestBody dto: AgencyDto): Int {
        return agencyService.create(dto)
    }
    //Обновить агентство по id
    //ручка не нужна
    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody dto: AgencyDto) {
        agencyService.update(id, dto)

    }
    //Удалить по id
    //ручка не нужна
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        agencyService.delete(id)

    }
    //добавить агентству услугу
    //ручка не нужна
    @PostMapping("/{id}/services/{serviceId}")
    fun addServiceToAgency(
        @RequestBody price: BigDecimal,
        @PathVariable("id") agencyId: Int,
        @PathVariable("serviceId") serviceId: Int
    ) {
        agencyService.addServiceToAgency(agencyId, serviceId, price)
    }

    //Получить список услуг агентства по id
    @GetMapping("/{id}/services")
    fun getListServices(@PathVariable agencyId: Int): ResponseEntity<List<AgencyServiceDto>> {
        val services = servicesAgency.getByAgencyId(agencyId)
        return ResponseEntity.ok(services)
    }
    //Получить все услуги всех агентств
    @GetMapping("/all/services")
    fun getAllServices(): ResponseEntity<List<AgencyServiceDto>> {
        val services = servicesAgency.getAll()
        return ResponseEntity.ok(services)
    }
}