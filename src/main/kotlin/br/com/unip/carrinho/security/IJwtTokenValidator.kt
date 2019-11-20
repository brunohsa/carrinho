package br.com.unip.cardapio.security

interface IJwtTokenValidator {

    fun obterDadosToken(token: String): TokenData
}