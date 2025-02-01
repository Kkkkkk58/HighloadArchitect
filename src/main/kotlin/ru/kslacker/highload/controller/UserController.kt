package ru.kslacker.highload.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.kslacker.highload.controller.mapping.toCommand
import ru.kslacker.highload.controller.mapping.toDto
import ru.kslacker.highload.controller.mapping.toResponse
import ru.kslacker.highload.controller.model.user.RegisterUserRequest
import ru.kslacker.highload.controller.model.user.RegisterUserResponse
import ru.kslacker.highload.controller.model.user.UserDto
import ru.kslacker.highload.service.domain.UserService

@Validated
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterUserRequest): RegisterUserResponse {
        val command = request.toCommand()
        return userService.createUser(command).toResponse()
    }

    @GetMapping("/{id}")
    fun getUser(@NotBlank @PathVariable id: String): UserDto {
        return userService.getUserById(id).toDto()
    }

    @GetMapping("/search")
    fun search(
        @NotBlank @RequestParam("first_name") firstNamePrefix: String,
        @NotBlank @RequestParam("second_name") secondNamePrefix: String
    ): List<UserDto> {
        return userService.findByFirstNameAndSecondNamePrefixes(firstNamePrefix, secondNamePrefix)
            .map { it.toDto() }
    }
}
