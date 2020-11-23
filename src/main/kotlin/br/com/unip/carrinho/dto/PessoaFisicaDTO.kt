package br.com.unip.carrinho.dto

class PessoaFisicaDTO {

    var nome: String? = null

    var sobrenome: String? = null

    var telefone: String? = null

    constructor()

    constructor(nome: String?, sobrenome: String?, telefone: String?) {
        this.nome = nome
        this.sobrenome = sobrenome
        this.telefone = telefone
    }
}

