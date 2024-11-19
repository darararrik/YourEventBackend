package yourevent.app.entity

import jakarta.persistence.*
import yourevent.app.dto.ServiceDto

@Entity
@Table(name = "services")
class ServiceEntity(
    @Id @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(name = "service_name")
    val name: String,

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    val serviceType: ServiceTypeEntity
) {
    fun toDto(): ServiceDto = ServiceDto(
        name = this.name,
        serviceType = this.serviceType.name
    )
}