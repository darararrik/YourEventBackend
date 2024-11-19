package yourevent.app.exception

import org.springframework.http.HttpStatus

class EventNotFoundException(eventId: Int) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "event.not.found",
        description = "Event not found with id = $eventId",
    )
)