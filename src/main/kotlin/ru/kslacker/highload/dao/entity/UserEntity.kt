package ru.kslacker.highload.dao.entity

import java.time.LocalDate
import java.time.OffsetDateTime

data class UserEntity(
    val id: String,
    val firstName: String,
    val secondName: String,
    val birthDate: LocalDate,
    val biography: String?,
    val city: String?,
    val passwordHash: String,
    val createdAt: OffsetDateTime,
    val createdBy: String,
    val updatedAt: OffsetDateTime,
    val updatedBy: String
)
