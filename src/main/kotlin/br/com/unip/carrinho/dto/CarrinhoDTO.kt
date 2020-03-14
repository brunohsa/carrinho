package br.com.unip.carrinho.dto

import java.math.BigDecimal
import java.time.LocalDateTime

class CarrinhoDTO {

    var id: String

    var produtos: List<ProdutoCarrinhoDTO> = emptyList()

    var valorTotal: BigDecimal

    var dataCriacao: LocalDateTime

    constructor(id: String, produtos: List<ProdutoCarrinhoDTO>, valorTotal: BigDecimal, dataCriacao: LocalDateTime) {
        this.id = id
        this.produtos = produtos
        this.valorTotal = valorTotal
        this.dataCriacao = dataCriacao
    }
}

