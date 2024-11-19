package yourevent.app.exception

import org.springframework.http.HttpStatus

class CategoryNotFoundException(categoryId: Int) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "Category.not.found",
        description = "Category not found with id = $categoryId",
    )
)