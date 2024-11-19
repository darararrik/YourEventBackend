package yourevent.app.entity.event

import jakarta.persistence.*
import yourevent.app.dto.event.EventDto
import yourevent.app.entity.CategoryEntity
import yourevent.app.entity.user.UserEntity
import java.sql.Timestamp

@Entity
@Table(name = "events")
class EventEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "event_id") val id: Int,

    @Column(name = "event_name", nullable = false) var name: String,

    @Column(name = "event_status", nullable = false) var status: String = Status.PLANNED.name,
    @Column(name = "event_price") var price: Int,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "event_user_id", nullable = false
    ) var userEntity: UserEntity,  // Ссылка на UserEntity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_category_id", nullable = false)
    var categoryEntity: CategoryEntity,  // Связь с категорией

    @Column(
        name = "event_created_date",
        nullable = false
    ) var createdDate: Timestamp = Timestamp(System.currentTimeMillis()),

    @Column(name = "event_start_date", nullable = false) var startDate: Timestamp,

    @Column(name = "event_end_date", nullable = false) var endDate: Timestamp,

    @Column(name = "event_address", nullable = false) var address: String,

    @Column(name = "event_description") var description: String,

    @Column(name = "event_is_completed") var isCompleted: Boolean = false,
    @Column(name = "event_people") var people: Int,

    ) {
    fun toDto(): EventDto = EventDto(
        id = this.id,
        name = this.name,
        status = this.status,
        categoryId = this.categoryEntity.id,
        createdDate = this.createdDate,
        startDate = this.startDate,
        endDate = this.endDate,
        address = this.address,
        description = this.description,
        userId = userEntity.id,
        isCompleted = isCompleted,
        people = people,
        price = price,
    )
}

