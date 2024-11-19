package yourevent.app.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import yourevent.app.dto.category.CategoryDto
import yourevent.app.entity.CategoryEntity
import yourevent.app.exception.CategoryNotFoundException

import yourevent.app.repository.CategoryRepo
import yourevent.app.service.CategoryService

@Service
class CategoryServiceImpl(
    private val categoryRepo: CategoryRepo
) : CategoryService {

    override fun getAll(): List<CategoryDto> {
        return categoryRepo.findAll().map { it.toDto() }
    }

    override fun getByID(id: Int): CategoryDto {
        return categoryRepo.findByIdOrNull(id)?.toDto() ?: throw CategoryNotFoundException(id)
    }

    override fun search(prefix: String): List<CategoryDto> {
        return categoryRepo.findByNameStartsWithIgnoreCaseOrderByName(prefix).map { it.toDto() }
    }

    private fun CategoryDto.toEntity() = CategoryEntity(
        id = 0,
        name = name,
        imageUrl = imageUrl,
    )

    private fun CategoryEntity.toDto() = CategoryDto(
        id = id,
        name = name,
        imageUrl = imageUrl,

        )


}