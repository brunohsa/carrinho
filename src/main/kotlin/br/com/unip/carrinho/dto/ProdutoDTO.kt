package br.com.unip.carrinho.dto

import java.math.BigDecimal

class ProdutoDTO {

    var id: String
    var nome: String
    var valor: BigDecimal

    constructor(id: String, nome: String, valor: BigDecimal) {
        this.id = id
        this.nome = nome
        this.valor = valor
    }
}