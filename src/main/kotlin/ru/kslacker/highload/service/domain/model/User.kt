package ru.kslacker.highload.service.domain.model

import java.time.LocalDate

data class User(
    val id: String,
    val firstName: String,
    val secondName: String,
    val birthDate: LocalDate,
    val biography: String?,
    val city: String?
)