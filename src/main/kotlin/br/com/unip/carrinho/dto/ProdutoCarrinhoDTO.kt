package br.com.unip.carrinho.dto

class ProdutoCarrinhoDTO {

    var produto: ProdutoDTO? = null
    var quantidade: Long? = 0

    constructor(produto: ProdutoDTO?, quantidade: Long?) {
        this.produto = produto
        this.quantidade = quantidade
    }
}