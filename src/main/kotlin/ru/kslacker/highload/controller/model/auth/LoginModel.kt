package ru.kslacker.highload.controller.model.auth

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val id: String,
    @field:NotBlank
    val password: String
)

data class LoginResponse(
    val token: String
)
