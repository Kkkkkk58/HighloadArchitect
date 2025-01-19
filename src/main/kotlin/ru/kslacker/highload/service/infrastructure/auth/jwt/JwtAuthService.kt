package ru.kslacker.highload.service.infrastructure.auth.jwt

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.kslacker.highload.service.infrastructure.auth.AuthService
import ru.kslacker.highload.service.infrastructure.auth.model.AuthCommand
import ru.kslacker.highload.service.infrastructure.auth.model.AuthResult

@Service
class JwtAuthService(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager
): AuthService {
    override fun login(command: AuthCommand): AuthResult {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(command.userId, command.password))

        val user = userDetailsService.loadUserByUsername(command.userId)
        val jwt = jwtService.generateToken(user)

        return AuthResult(jwt)
    }
}