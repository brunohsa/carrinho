package br.com.unip.carrinho.dto

class ProdutoDTO {

    var id: String
    var nome: String
    var valor: Double

    constructor(id: String, nome: String, valor: Double) {
        this.id = id
        this.nome = nome
        this.valor = valor
    }
}