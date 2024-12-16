package yourevent.app

import io.github.cdimascio.dotenv.Dotenv
import io.jsonwebtoken.security.Keys
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@SpringBootApplication
class AppApplication


// Основная функция
fun main(args: Array<String>) {
    Dotenv.load()
    val secretKey: String = Dotenv.load().get("JWT_SECRET_KEY")!!
    val encodedKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    println(encodedKey)  // Выведет Base64 строку
    runApplication<AppApplication>(*args)
}