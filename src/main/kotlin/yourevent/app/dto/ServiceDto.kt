package yourevent.app.dto

import jakarta.persistence.*
import yourevent.app.entity.ServiceTypeEntity

data class ServiceDto(
    val id: Int? = null,
    val name: String,
    val serviceType: String
)
