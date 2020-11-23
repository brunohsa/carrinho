package br.com.unip.carrinho.dto

class AdicionarProdutoCarrinhoDTO {

    var id: String
    var quantidade: Int = 1
    var observacoes: String? = ""

    constructor(id: String, observacoes: String?, quantidade: Int) {
        this.id = id
        this.observacoes = observacoes
        this.quantidade = quantidade
    }
}