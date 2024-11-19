package yourevent.app.entity

import jakarta.persistence.*
import yourevent.app.entity.event.EventEntity

@Entity
@Table(name = "categories")
class CategoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@OneToMany(mappedBy = "categoryId")
    @Column(name = "cat_id") val id: Int,
    @Column(name = "cat_url_photo")
    val imageUrl: String,
    @Column(name = "cat_name")
    val name: String,
    @OneToMany(
        mappedBy = "categoryEntity",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )


    val events: List<EventEntity> = mutableListOf()


)