package ru.kslacker.highload.service.infrastructure.auth

import ru.kslacker.highload.service.infrastructure.auth.model.AuthCommand
import ru.kslacker.highload.service.infrastructure.auth.model.AuthResult

interface AuthService {
    fun login(command: AuthCommand): AuthResult
}