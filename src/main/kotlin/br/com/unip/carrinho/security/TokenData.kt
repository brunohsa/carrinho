package br.com.unip.cardapio.security

class TokenData {

    val id: String?
    val login: String
    val token: String
    val scopes: List<String>

    constructor(id: String?, login: String, token: String, scopes: List<String>) {
        this.id = id
        this.login = login
        this.token = token
        this.scopes = scopes
    }
}