package br.com.unip.cardapio.security.util

import br.com.unip.cardapio.security.JwtTokenValidator
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
class TokenUtil {

    private val TOKEN_TAG = "token"

    @Value("\${autenticacao.key}")
    private val key: String? = null

    val jwt: JwtTokenValidator

    constructor(jwt: JwtTokenValidator) {
        this.jwt = jwt
    }


    @Throws(/*TokenExpiradoException::class, TokenInvalidoException::class*/java.lang.RuntimeException::class)
    fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken {
        val token = request.getHeader(TOKEN_TAG)
        jwt.obterDadosToken(token)
        if (token.isNullOrEmpty()) {
//            throw TokenInvalidoException()
            throw RuntimeException()
        }
        try {
            val user = Jwts.parser().setSigningKey(key).parseClaimsJws(token).body.subject
            return UsernamePasswordAuthenticationToken(user, null, Collections.emptyList())
        } catch (e: ExpiredJwtException) {
//            throw TokenExpiradoException()
            throw RuntimeException()
        }
    }
}