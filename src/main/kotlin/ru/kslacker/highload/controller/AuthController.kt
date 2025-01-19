package ru.kslacker.highload.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.kslacker.highload.controller.mapping.toCommand
import ru.kslacker.highload.controller.mapping.toResponse
import ru.kslacker.highload.controller.model.auth.LoginRequest
import ru.kslacker.highload.controller.model.auth.LoginResponse
import ru.kslacker.highload.service.infrastructure.auth.AuthService

@RestController
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): LoginResponse {
        val command = request.toCommand()
        return authService.login(command).toResponse()
    }
}
