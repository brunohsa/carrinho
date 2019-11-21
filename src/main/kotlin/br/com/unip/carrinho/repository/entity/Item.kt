package br.com.unip.carrinho.repository.entity

class Item {
    var produtoId: String? = null

    var observacoes: String? = ""

    var quantidade: Long? = 0

    var cliente: String? = ""

    constructor(produtoId: String?, observacoes: String?, quantidade: Long?, cliente: String?) {
        this.produtoId = produtoId
        this.observacoes = observacoes
        this.quantidade = quantidade
        this.cliente = cliente
    }
}