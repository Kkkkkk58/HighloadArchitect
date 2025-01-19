package ru.kslacker.highload.controller.mapping

import ru.kslacker.highload.controller.model.user.UserDto
import ru.kslacker.highload.service.domain.model.User

fun User.toDto(): UserDto {
    return UserDto(
        firstName = firstName,
        secondName = secondName,
        birthDate = birthDate,
        biography = biography,
        city = city
    )
}
