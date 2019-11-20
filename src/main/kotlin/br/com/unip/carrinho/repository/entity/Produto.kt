package br.com.unip.carrinho.repository.entity

import java.math.BigDecimal

class Produto {

    var id: String? = null

    var nome: String? = null

    var valor: BigDecimal? = null

    constructor(id: String?, nome: String?, valor: BigDecimal?) {
        this.id = id
        this.nome = nome
        this.valor = valor
    }
}