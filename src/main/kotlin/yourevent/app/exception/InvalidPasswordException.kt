package yourevent.app.exception

import org.springframework.http.HttpStatus

class InvalidPasswordException(details: String) : BaseException(
    HttpStatus.BAD_REQUEST,
    ApiError(
        errorCode = "bad.password",
        description = "Invalid password: $details"
    )
)