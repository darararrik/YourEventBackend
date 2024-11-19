package yourevent.app.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import yourevent.app.dto.user.UserDto
import yourevent.app.service.UserService

@RestController
@RequestMapping("yourevent/user")
class UserController(val userService: UserService) {
    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails): UserDto =
        userService.getByEmail(userDetails.username)
}