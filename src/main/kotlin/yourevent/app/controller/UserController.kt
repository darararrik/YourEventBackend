package yourevent.app.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import yourevent.app.dto.UpdateEmailRequest
import yourevent.app.dto.UpdateNameRequest
import yourevent.app.dto.UpdatePasswordRequest
import yourevent.app.dto.auth.AuthenticationResponse
import yourevent.app.dto.user.UserDto
import yourevent.app.service.UserService

@RestController
@RequestMapping("yourevent/user")
class UserController(val userService: UserService) {
    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails): UserDto =
        userService.getByEmail(userDetails.username)


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        userService.deleteUser(id)

    }

    @PatchMapping("/me/update/password")
    fun updatePassword(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: UpdatePasswordRequest
    ): AuthenticationResponse {
        // Сменить пароль
        return userService.updatePassword(userDetails.username, request.newPassword, request.oldPassword)
    }

    @PatchMapping("me/update/email")
    fun updateEmail(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: UpdateEmailRequest
    ): AuthenticationResponse {
        return userService.updateEmail(userDetails.username, request.newEmail)
    }

    @PatchMapping("me/update/name")
    fun updateName(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: UpdateNameRequest
    ): UserDto {
        return userService.updateName(userDetails.username, request.name, request.surname)
    }

}