package br.com.unip.carrinho.dto

class ProdutoCarrinhoDTO {

    lateinit var produto: ProdutoDTO
    var observacoes: String? = ""
    var quantidade: Long = 0

    constructor(produto: ProdutoDTO, observacoes: String?, quantidade: Long) : this(observacoes, quantidade) {
        this.produto = produto
    }

    constructor(observacoes: String?, quantidade: Long) {
        this.observacoes = observacoes
        this.quantidade = quantidade
    }


}