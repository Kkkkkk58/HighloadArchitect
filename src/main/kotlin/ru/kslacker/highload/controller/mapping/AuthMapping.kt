package ru.kslacker.highload.controller.mapping

import ru.kslacker.highload.controller.model.auth.LoginRequest
import ru.kslacker.highload.controller.model.auth.LoginResponse
import ru.kslacker.highload.service.infrastructure.auth.model.AuthCommand
import ru.kslacker.highload.service.infrastructure.auth.model.AuthResult

fun LoginRequest.toCommand(): AuthCommand {
    return AuthCommand(
        userId = id,
        password = password
    )
}

fun AuthResult.toResponse(): LoginResponse {
    return LoginResponse(
        token = token
    )
}