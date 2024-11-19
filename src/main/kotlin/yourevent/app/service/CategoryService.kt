package yourevent.app.service

import yourevent.app.dto.category.CategoryDto

interface CategoryService {
    fun getAll() : List<CategoryDto>

    fun getByID(id: Int): CategoryDto

    fun search(prefix: String) : List<CategoryDto>
}