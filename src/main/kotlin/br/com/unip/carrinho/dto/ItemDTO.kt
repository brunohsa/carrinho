package br.com.unip.carrinho.dto

class ItemDTO {
    var produto: ProdutoCardapioDTO? = null
    var observacoes: String? = ""
    var quantidade: Long? = 0
    var cliente: String? = ""

    constructor(produto: ProdutoCardapioDTO?, observacoes: String?, quantidade: Long?, cliente: String?) {
        this.produto = produto
        this.observacoes = observacoes
        this.quantidade = quantidade
        this.cliente = cliente
    }
}