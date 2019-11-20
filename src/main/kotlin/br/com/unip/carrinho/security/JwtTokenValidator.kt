package br.com.unip.cardapio.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.InvalidKeyException

@Component
class JwtTokenValidator : IJwtTokenValidator {

    @Value("\${autenticacao.key}")
    private val key: String? = null

    override fun obterDadosToken(token: String): TokenData {

        if (key.isNullOrEmpty())
            throw InvalidKeyException()

        val jwtParser = Jwts.parser().setSigningKey(key)

        if (token == null || token.isEmpty()) {
//            throw InvalidTokenException("Null or empty token")
            throw RuntimeException()
        }

        val cleanToken = token.replace("Auth", "")

//        log.debug("Validating token {}", cleanToken)

        try {
            val jwsClaims = jwtParser.parseClaimsJws(cleanToken)

            val id = jwsClaims.body.issuer
            val login = jwsClaims.body.subject
            val scopes = jwsClaims.body.get("scopes", List::class.java)

            return TokenData(id, login, cleanToken, scopes as List<String>)

        } catch (e: ExpiredJwtException) {
//            log.error("Expired token {}", cleanToken, e)
//            throw ExpiredTokenException("Expired token $cleanToken", e)
            throw RuntimeException()
        } catch (e: Exception) {
//            log.error("Invalid token {}", cleanToken, e)
//            throw InvalidTokenException("Invalid token $cleanToken", e)
            throw RuntimeException()
        }
    }
}