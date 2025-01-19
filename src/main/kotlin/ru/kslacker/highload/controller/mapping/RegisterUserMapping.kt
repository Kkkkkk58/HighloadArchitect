package ru.kslacker.highload.controller.mapping

import ru.kslacker.highload.controller.model.user.RegisterUserRequest
import ru.kslacker.highload.controller.model.user.RegisterUserResponse
import ru.kslacker.highload.service.domain.model.CreateUserCommand
import ru.kslacker.highload.service.domain.model.CreateUserResult

fun RegisterUserRequest.toCommand(): CreateUserCommand {
    return CreateUserCommand(
        firstName = firstName,
        secondName = secondName,
        birthDate = birthDate,
        biography = biography,
        city = city,
        password = password
    )
}

fun CreateUserResult.toResponse(): RegisterUserResponse {
    return RegisterUserResponse(
        userId = userId
    )
}
