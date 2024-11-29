package yourevent.app.entity

import jakarta.persistence.*
import yourevent.app.dto.AgencyServiceDto
import yourevent.app.entity.agency.AgencyEntity
import java.math.BigDecimal


@Entity
@Table(name = "agency_services")
data class AgencyServiceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_service_id", nullable = false)
    val id: Int? = 0,

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    val agency: AgencyEntity,

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    val service: ServiceEntity,

    @Column(name = "service_price", nullable = false)
    val price: BigDecimal


) {
    fun toDto(): AgencyServiceDto = AgencyServiceDto(
        agencyName = agency.name,
        serviceName = service.name,
        serviceTypeName = service.serviceType.name,
        price = price,
    )
}
