//package yourevent.app.controller
//
//import org.springframework.web.bind.annotation.*
//import yourevent.app.dto.ServiceDto
//import yourevent.app.service.ServiceService
//
//@RestController
//@RequestMapping("yourevent/services")
//class ServiceController(private val serviceService: ServiceService) {
//
//    @GetMapping("/{id}")
//    fun getByID(@PathVariable("id") id: Int): ServiceDto =
//        serviceService.getByAgencyId(id)
//
//}