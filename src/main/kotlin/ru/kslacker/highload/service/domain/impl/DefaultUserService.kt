package ru.kslacker.highload.service.domain.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kslacker.highload.dao.UserDao
import ru.kslacker.highload.dao.entity.UserEntity
import ru.kslacker.highload.exception.UserNotFoundException
import ru.kslacker.highload.service.domain.UserService
import ru.kslacker.highload.service.domain.mapping.toDomainModel
import ru.kslacker.highload.service.domain.model.CreateUserCommand
import ru.kslacker.highload.service.domain.model.CreateUserResult
import ru.kslacker.highload.service.domain.model.User
import ru.kslacker.highload.service.infrastructure.utils.id.IdGenerator
import java.time.Clock
import java.time.OffsetDateTime

@Service
class DefaultUserService(
    private val userDao: UserDao,
    private val idGenerator: IdGenerator<UserEntity>,
    private val passwordEncoder: PasswordEncoder,
    private val clock: Clock
): UserService {
    override fun getUserById(id: String): User {
        val user = userDao.findById(id) ?: throw UserNotFoundException.withUserId(id)
        return user.toDomainModel()
    }

    override fun findByFirstNameAndSecondNamePrefixes(firstNamePrefix: String, secondNamePrefix: String): List<User> {
        return userDao.findByFirstNamePrefixAndSecondNamePrefix(firstNamePrefix, secondNamePrefix)
            .map { it.toDomainModel() }
    }

    @Transactional
    override fun createUser(command: CreateUserCommand): CreateUserResult {
        val userId = idGenerator.getNextId()
        val passwordHash = passwordEncoder.encode(command.password)
        val userEntity = command.toEntity(userId, passwordHash, OffsetDateTime.now(clock))

        userDao.insert(userEntity)
        return CreateUserResult(userId)
    }
}


private fun CreateUserCommand.toEntity(id: String, passwordHash: String, now: OffsetDateTime): UserEntity {
    return UserEntity(
        id = id,
        firstName = firstName,
        secondName = secondName,
        birthDate = birthDate,
        biography = biography,
        city = city,
        passwordHash = passwordHash,
        createdAt = now,
        createdBy = id,
        updatedAt = now,
        updatedBy = id
    )
}
