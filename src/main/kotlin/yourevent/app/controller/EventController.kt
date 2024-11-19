package yourevent.app.controller

import org.springframework.web.bind.annotation.*
import yourevent.app.dto.event.EventDto
import yourevent.app.service.EventService

@RestController
@RequestMapping("yourevent/events")
class EventController(
    private val eventService: EventService,
) {
    //yourevent/events?page=1
    @GetMapping()
    fun getAll(@RequestParam("page") pageIndex: Int): List<EventDto> =
        eventService.getAll(pageIndex)
    @GetMapping("/{id}")
    fun getByUserID(@PathVariable("id") id: Int): List<EventDto> =
        eventService.getByUserID(id)
    //yourevent/events/search?prefix=Д
    @GetMapping("/search")
    fun searchEvents(@RequestParam("prefix") prefix: String): List<EventDto> =
        eventService.search(prefix)

    @PostMapping()
    fun create(@RequestBody dto: EventDto): Int {
        return eventService.create(dto)
    }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        eventService.delete(id)

    }

}
