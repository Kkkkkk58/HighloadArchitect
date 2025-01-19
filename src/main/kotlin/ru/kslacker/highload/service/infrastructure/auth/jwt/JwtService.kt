package ru.kslacker.highload.service.infrastructure.auth.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.kslacker.highload.service.infrastructure.auth.model.CustomUserDetails
import java.time.Clock
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${auth.token.signing.key}")
    private val jwtSigningKey: String,
    private val clock: Clock
) {
    companion object {
        private const val TOKEN_EXPIRATION_MILLIS: Long = 24 * 60 * 1000
    }

    fun extractUserName(token: String): String? {
        return extractClaim(token) { it.subject }
    }

    fun generateToken(userDetails: UserDetails): String {
        val extraClaims = mutableMapOf<String, Any>()
        if (userDetails is CustomUserDetails) {
            extraClaims["id"] = userDetails.username
        }

        return generateToken(extraClaims, userDetails)
    }

    private fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        val now = getCurrentDate()
        val expirationDate = Date.from(now.toInstant().plusMillis(TOKEN_EXPIRATION_MILLIS))

        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(now)
            .expiration(expirationDate)
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUserName(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        val now = getCurrentDate()
        return extractExpiration(token)?.before(now) ?: false
    }

    private fun extractExpiration(token: String): Date? {
        return extractClaim(token) { it.expiration }
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        val jwtParser = Jwts.parser().verifyWith(getSigningKey()).build()

        return jwtParser.parseUnsecuredClaims(token).payload
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtSigningKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun getCurrentDate(): Date {
        return Date.from(clock.instant())
    }
}
