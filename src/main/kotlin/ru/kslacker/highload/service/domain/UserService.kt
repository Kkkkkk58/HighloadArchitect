package ru.kslacker.highload.service.domain

import ru.kslacker.highload.service.domain.model.CreateUserCommand
import ru.kslacker.highload.service.domain.model.CreateUserResult
import ru.kslacker.highload.service.domain.model.User

interface UserService {
    fun getUserById(id: String): User
    fun findByFirstNameAndSecondNamePrefixes(firstNamePrefix: String, secondNamePrefix: String): List<User>
    fun createUser(command: CreateUserCommand): CreateUserResult
}
