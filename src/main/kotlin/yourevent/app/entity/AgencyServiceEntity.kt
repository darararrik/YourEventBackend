package yourevent.app.entity

import jakarta.persistence.*
import yourevent.app.entity.agency.AgencyEntity


@Entity
@Table(name = "agency_services")
data class AgencyServiceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = 0,

    @ManyToOne
    @JoinColumn(name = "ag_id", nullable = false)
    val agency: AgencyEntity,

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    val service: ServiceEntity
)
