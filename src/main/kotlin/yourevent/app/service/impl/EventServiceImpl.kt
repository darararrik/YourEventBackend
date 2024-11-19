package yourevent.app.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import yourevent.app.repository.EventRepo
import org.springframework.stereotype.Service
import yourevent.app.dto.event.EventDto
import yourevent.app.exception.EventNotFoundException
import yourevent.app.repository.CategoryRepo
import yourevent.app.repository.UserRepo
import yourevent.app.service.EventService

@Service
class EventServiceImpl(
    private val userRepo: UserRepo,
    private val eventRepo: EventRepo,
    private val categoryRepo: CategoryRepo,
) : EventService {
    override fun getAll(pageIndex: Int): List<EventDto> {
        return eventRepo.findByOrderByName(PageRequest.of(pageIndex, 20)).map { it.toDto() }
    }

    override fun getByEventID(id: Int): EventDto {
        return eventRepo.findByIdOrNull(id)?.toDto() ?: throw EventNotFoundException(id)
    }

    override fun getByUserID(id: Int): List<EventDto> {
        return eventRepo.findByUserEntityId(id).map { it.toDto() }
    }

    override fun search(prefix: String): List<EventDto> =
        eventRepo.findByNameStartsWithIgnoreCaseOrderByName(prefix).map { it.toDto() }

    override fun create(dto: EventDto): Int {
        val existingUserEntity = userRepo.findById(dto.userId)
            .orElseThrow { RuntimeException("User not found") }
        val categoryEntity = categoryRepo.findById(dto.categoryId)
            .orElseThrow { RuntimeException("User not found") }
        return eventRepo.save(dto.toEntity(existingUserEntity, categoryEntity)).id
    }


    override fun delete(id: Int) {
        val existingEvent = eventRepo.findByIdOrNull(id) ?: throw EventNotFoundException(id)
        eventRepo.deleteById(existingEvent.id)
    }


}





