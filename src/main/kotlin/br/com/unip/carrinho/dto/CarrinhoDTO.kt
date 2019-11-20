package br.com.unip.carrinho.dto

import java.math.BigDecimal

class CarrinhoDTO {

    var id: String? = ""

    var produtos: List<ProdutoCarrinhoDTO>? = emptyList()

    var valorTotal: BigDecimal?

    constructor(id: String?, produtos: List<ProdutoCarrinhoDTO>?, valorTotal: BigDecimal?) {
        this.id = id
        this.produtos = produtos
        this.valorTotal = valorTotal
    }
}

