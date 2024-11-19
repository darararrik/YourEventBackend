package yourevent.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourevent.app.entity.CategoryEntity

interface CategoryRepo : JpaRepository<CategoryEntity, Int> {
    fun findByOrderByName() : List<CategoryEntity>
    fun findByNameStartsWithIgnoreCaseOrderByName(prefix: String): List<CategoryEntity>
}