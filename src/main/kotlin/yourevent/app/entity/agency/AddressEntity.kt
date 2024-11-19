package yourevent.app.entity.agency

import yourevent.app.dto.agencyDto.AddressDto
data class AddressEntity(
    var city: String = "",
    var street: String = "",
    var building: String = ""
)

