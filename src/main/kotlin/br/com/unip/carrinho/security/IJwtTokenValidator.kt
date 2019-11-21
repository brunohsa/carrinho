package br.com.unip.carrinho.security

interface IJwtTokenValidator {

    fun obterDadosToken(token: String): TokenData
}