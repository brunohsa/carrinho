package br.com.unip.carrinho.dto

class ProdutoCarrinhoDTO {

    lateinit var produto: ProdutoDTO
    var observacoes: String? = ""
    var quantidade: Int = 0

    constructor(produto: ProdutoDTO, observacoes: String?, quantidade: Int) : this(observacoes, quantidade) {
        this.produto = produto
    }

    constructor(observacoes: String?, quantidade: Int) {
        this.observacoes = observacoes
        this.quantidade = quantidade
    }
}