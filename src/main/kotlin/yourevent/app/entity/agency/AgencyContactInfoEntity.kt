package yourevent.app.entity.agency

import yourevent.app.dto.agencyDto.AgencyContactInfoDto

data class AgencyContactInfoEntity(
    var email: String = "",
    var phone: String = "",
    var address: AddressEntity = AddressEntity(),
)
