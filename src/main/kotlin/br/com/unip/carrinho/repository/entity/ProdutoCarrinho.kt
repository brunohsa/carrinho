package br.com.unip.carrinho.repository.entity

class ProdutoCarrinho {

    var produto: Produto? = null

    var quantidade: Long? = 0

    constructor()

    constructor(produto: Produto?, quantidade: Long?) {
        this.produto = produto
        this.quantidade = quantidade
    }
}