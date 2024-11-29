package yourevent.app.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import yourevent.app.dto.user.UserDto
import yourevent.app.service.UserService

@RestController
@RequestMapping("yourevent/users")
class UsersController(val userService: UserService) {
    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails): UserDto =
    userService.getByEmail(userDetails.username)


    @GetMapping()
    fun getAll(@RequestParam("page") pageIndex: Int): List<UserDto> =
        userService.getAll(pageIndex)


    @GetMapping("/{id}")
    fun getByID(@PathVariable("id") id: Int): UserDto =
        userService.getByID(id)

    @GetMapping("/search")
    fun search(@RequestParam("prefix") prefix: String): List<UserDto> =
        userService.searchUser(prefix)

    @GetMapping("/findByEmail")

    fun findByEmail(@RequestParam("email") email: String): UserDto =
        userService.getByEmail(email)

    @PostMapping()
    fun create(@RequestBody dto: UserDto): Int {
        return userService.createUser(dto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody dto: UserDto) {
        userService.updateUser(id, dto)

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        userService.deleteUser(id)

    }
}