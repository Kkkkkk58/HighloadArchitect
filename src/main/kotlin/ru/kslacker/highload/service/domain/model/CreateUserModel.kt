package ru.kslacker.highload.service.domain.model

import java.time.LocalDate

data class CreateUserCommand(
    val firstName: String,
    val secondName: String,
    val birthDate: LocalDate,
    val biography: String?,
    val city: String?,
    val password: String
)

data class CreateUserResult(
    val userId: String
)
