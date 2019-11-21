package br.com.unip.carrinho.repository.entity

class ProdutoCarrinho {

    var produtoId: String? = null

    var quantidade: Long? = 0

    var observacoes: String? = ""

    constructor()

    constructor(produtoId: String?, quantidade: Long?, observacoes: String?) {
        this.produtoId = produtoId
        this.quantidade = quantidade
        this.observacoes = observacoes
    }
}