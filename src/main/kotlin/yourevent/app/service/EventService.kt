package yourevent.app.service

import yourevent.app.dto.event.EventDto

interface EventService {
    fun getAll(pageIndex: Int) : List<EventDto>

    fun getByEventID(id: Int): EventDto
    fun getByUserID(id: Int): List<EventDto>

    fun search(prefix: String) : List<EventDto>

    fun create(dto: EventDto) : Int
    fun delete(id: Int)
}