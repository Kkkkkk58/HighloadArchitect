package ru.kslacker.highload.service.infrastructure.auth.model

data class AuthCommand(
    val userId: String,
    val password: String
)

data class AuthResult(
    val token: String
)
