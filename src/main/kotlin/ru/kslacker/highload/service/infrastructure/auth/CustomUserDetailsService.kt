package ru.kslacker.highload.service.infrastructure.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.kslacker.highload.dao.UserDao
import ru.kslacker.highload.exception.UserNotFoundException
import ru.kslacker.highload.service.infrastructure.auth.mapping.toCustomUserDetails

@Service
class CustomUserDetailsService(
    private val userDao: UserDao
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        requireNotNull(username) { "username must not be null" }

        return userDao.findById(username)?.toCustomUserDetails()
            ?: throw UserNotFoundException.withUserId(username)
    }
}
