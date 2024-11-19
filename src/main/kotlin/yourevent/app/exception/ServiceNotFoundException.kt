package yourevent.app.exception

import org.springframework.http.HttpStatus
import yourevent.app.exception.UserNotFoundException.IdentifierType

class ServiceNotFoundException(
    identifier: String,
    type: IdentifierType
) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "Service.not.found",
        description = "Service not found with $type = $identifier",
    )
) {
    enum class IdentifierType(val description: String) {
        ID("id"),
    }

    constructor(userId: Int) : this(userId.toString(), IdentifierType.ID)
}

