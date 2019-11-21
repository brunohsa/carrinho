package br.com.unip.carrinho.dto

class AdicionarProdutoCarrinhoDTO {

    var id: String? = ""
    var quantidade: Long? = 0
    var observacoes: String? = ""

    constructor(id: String?, quantidade: Long?, observacoes: String?) {
        this.id = id
        this.quantidade = quantidade
        this.observacoes = observacoes
    }
}