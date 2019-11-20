package br.com.unip.carrinho.dto

import java.math.BigDecimal

class ProdutoDTO {

    var id: String? = null
    var nome: String? = null
    var valor: BigDecimal? = null

    constructor(id: String?, nome: String?, valor: BigDecimal?) {
        this.id = id
        this.nome = nome
        this.valor = valor
    }
}