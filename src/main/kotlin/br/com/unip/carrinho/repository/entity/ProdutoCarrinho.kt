package br.com.unip.carrinho.repository.entity

class ProdutoCarrinho {

    lateinit var produto: Produto

    var observacoes: String? = ""

    var quantidade: Int = 0

    constructor()

    constructor(produto: Produto, observacoes: String?, quantidade: Int) {
        this.produto = produto
        this.observacoes = observacoes
        this.quantidade = quantidade
    }
}