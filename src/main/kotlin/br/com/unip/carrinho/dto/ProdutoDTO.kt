package br.com.unip.carrinho.dto

class ProdutoDTO {

    var id: String
    var cardapioId: String
    var nome: String
    var valor: Double

    constructor(id: String, cardapioId: String, nome: String, valor: Double) {
        this.id = id
        this.cardapioId = cardapioId
        this.nome = nome
        this.valor = valor
    }
}