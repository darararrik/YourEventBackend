package yourevent.app.exception

import org.springframework.context.annotation.Description

data class ApiError(
    val errorCode: String, //not found
    val description: String
)
