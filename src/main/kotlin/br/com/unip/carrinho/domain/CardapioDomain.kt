package br.com.unip.carrinho.domain

import br.com.unip.carrinho.domain.campos.Nome

class CardapioDomain {

    val nome: Nome

    constructor(nome: String?) {
        this.nome = Nome(nome)
    }
}