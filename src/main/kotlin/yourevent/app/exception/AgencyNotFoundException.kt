package yourevent.app.exception

import org.springframework.http.HttpStatus

class AgencyNotFoundException(agencyId: Int) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "agency.not.found",
        description = "Agency not found with id = $agencyId",
    )
)