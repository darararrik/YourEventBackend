package yourevent.app.exception

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException(ex: ExpiredJwtException): ResponseEntity<String> {
        // Логируем сообщение об истекшем токене
        println("JWT expired: ${ex.message}")

        // Возвращаем статус 401 (Unauthorized)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token expired")
    }
}
