package br.com.unip.carrinho.dto

class AdicionarProdutoCarrinhoDTO {

    var id: String
    var quantidade: Long = 1
    var observacoes: String? = ""

    constructor(id: String, observacoes: String?, quantidade: Long) {
        this.id = id
        this.observacoes = observacoes
        this.quantidade = quantidade
    }
}