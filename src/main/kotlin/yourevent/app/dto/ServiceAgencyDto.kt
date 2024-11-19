package yourevent.app.dto

import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import yourevent.app.entity.ServiceEntity
import yourevent.app.entity.agency.AgencyEntity
import java.math.BigDecimal

data class ServiceAgencyDto(
    val agencyName: String,        // Название агентства
    val serviceName: String,       // Название услуги
    val serviceTypeName: String,   // Тип услуги
    val price: BigDecimal
)
