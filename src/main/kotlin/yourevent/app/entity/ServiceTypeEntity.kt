package yourevent.app.entity

import jakarta.persistence.*
import yourevent.app.dto.ServiceTypeDto


@Entity
@Table(name = "service_types")
class ServiceTypeEntity(
    @Id @Column(name = "service_type_id") @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int = 0,
    @Column(name = "service_type_name") val name: String
) {
    fun toDto(serviceTypeEntity: ServiceTypeEntity) = ServiceTypeDto(name = name)
}
