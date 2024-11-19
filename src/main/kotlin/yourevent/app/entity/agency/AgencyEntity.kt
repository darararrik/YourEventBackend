package yourevent.app.entity.agency

import jakarta.persistence.*
import yourevent.app.converter.AgencyContactInfoConverter
import yourevent.app.dto.agencyDto.AddressDto
import yourevent.app.dto.agencyDto.AgencyContactInfoDto
import yourevent.app.dto.agencyDto.AgencyDto
import java.sql.Timestamp


@Entity
@Table(name = "agencies")
class AgencyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    val id: Int,

    @Column(name = "agency_name")
    var name: String,


    @Column(name = "agency_registration_date")
    var registrationDate: Timestamp,

    @Column(name = "agency_verified")
    var verified: Boolean,

    @Column(name = "agency_contact_info", columnDefinition = "jsonb")
    @Convert(converter = AgencyContactInfoConverter::class)
    val contactInfo: AgencyContactInfoEntity,
//
//    @OneToMany(mappedBy = "agencies")
//    val agencyServices: List<AgencyService> = emptyList()
) {



}


