package yourevent.app.dto

import java.math.BigDecimal

data class AgencyServiceDto(
    val agencyName: String,        // Название агентства
    val serviceName: String,       // Название услуги
    val serviceTypeName: String,   // Тип услуги
    val price: BigDecimal
)
