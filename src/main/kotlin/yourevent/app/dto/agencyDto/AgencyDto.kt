package yourevent.app.dto.agencyDto

import yourevent.app.entity.agency.AddressEntity
import yourevent.app.entity.agency.AgencyContactInfoEntity
import yourevent.app.entity.agency.AgencyEntity
import java.sql.Timestamp

data class AgencyDto(

    val id: Int? = null,
    val name: String,
    val registrationDate: Timestamp,
    val verified: Boolean,
    val contactInfo: AgencyContactInfoDto,
)
{

}

