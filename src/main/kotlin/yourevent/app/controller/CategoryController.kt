package yourevent.app.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yourevent.app.dto.category.CategoryDto
import yourevent.app.service.CategoryService

@RestController
@RequestMapping("yourevent/categories")
class CategoryController(val categoryService: CategoryService) {
    @GetMapping()
    fun getList(): List<CategoryDto> =  categoryService.getAll()
}