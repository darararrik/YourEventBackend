package yourevent.app.exception

import org.springframework.http.HttpStatus

class UserNotFoundException(
    identifier: String,
    type: IdentifierType
) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "User.not.found",
        description = "User not found with $type = $identifier",
    )
) {
    enum class IdentifierType(val description: String) {
        ID("id"),
        EMAIL("email")
    }

    constructor(userId: Int) : this(userId.toString(), IdentifierType.ID)
    constructor(email: String) : this(email, IdentifierType.EMAIL)
}
