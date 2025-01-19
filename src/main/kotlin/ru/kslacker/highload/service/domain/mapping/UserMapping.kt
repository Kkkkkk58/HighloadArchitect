package ru.kslacker.highload.service.domain.mapping

import ru.kslacker.highload.dao.entity.UserEntity
import ru.kslacker.highload.service.domain.model.User

fun UserEntity.toDomainModel(): User {
    return User(
        id = id,
        firstName = firstName,
        secondName = secondName,
        birthDate = birthDate,
        biography = biography,
        city = city
    )
}
