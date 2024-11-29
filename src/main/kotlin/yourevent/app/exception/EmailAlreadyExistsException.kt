package yourevent.app.exception

import org.springframework.http.HttpStatus

class EmailAlreadyExistsException(newEmail: String) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "email.exists",
        description = "User with this $newEmail already exist",
    )
)
