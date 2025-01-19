package ru.kslacker.highload.exception

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

class UserNotFoundException(
    override val message: String
): HttpStatusCodeException(HttpStatus.NOT_FOUND) {
    companion object {
        fun withUserId(userId: String): UserNotFoundException {
            return UserNotFoundException("User with id $userId not found")
        }
    }
}