package ru.kslacker.highload.service.infrastructure.auth.mapping

import ru.kslacker.highload.dao.entity.UserEntity
import ru.kslacker.highload.service.infrastructure.auth.model.CustomUserDetails

fun UserEntity.toCustomUserDetails(): CustomUserDetails {
    return CustomUserDetails(
        username = id,
        password = passwordHash
    )
}