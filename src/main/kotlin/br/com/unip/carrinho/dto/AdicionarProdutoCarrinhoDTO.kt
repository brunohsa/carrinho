package br.com.unip.carrinho.dto

class AdicionarProdutoCarrinhoDTO {

    var id: String? = ""
    var quantidade: Long? = 0

    constructor(id: String?, quantidade: Long?) {
        this.id = id
        this.quantidade = quantidade
    }
}