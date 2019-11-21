package br.com.unip.carrinho.dto

class ProdutoCarrinhoDTO {

    var produto: ProdutoDTO? = null
    var quantidade: Long? = 0
    var observacoes: String? = ""

    constructor(produto: ProdutoDTO?, quantidade: Long?, observacoes: String?) {
        this.produto = produto
        this.quantidade = quantidade
        this.observacoes = observacoes
    }
}