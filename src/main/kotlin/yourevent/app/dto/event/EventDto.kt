package yourevent.app.dto.event

import yourevent.app.entity.CategoryEntity
import yourevent.app.entity.event.EventEntity
import yourevent.app.entity.user.UserEntity
import java.sql.Timestamp

data class EventDto(
    val id: Int? = null,
    val name: String,
    val status: String?,
    val price: Int,
    val categoryId: Int,
    var categoryName: String,
    val createdDate: Timestamp?,
    val startDate: Timestamp,
    val endDate: Timestamp,
    val address: String,
    val description: String,
    val userId: Int,
    var people: Int,
) {
    fun toEntity(userEntity: UserEntity, categoryEntity: CategoryEntity): EventEntity = EventEntity(
        id = 0, // ID будет автоматически сгенерирован
        name = this.name,
        categoryEntity = categoryEntity,
        startDate = this.startDate,
        endDate = this.endDate,
        address = this.address,
        description = this.description,
        userEntity = userEntity,
        people = this.people,
        price = this.price,
    )
}

