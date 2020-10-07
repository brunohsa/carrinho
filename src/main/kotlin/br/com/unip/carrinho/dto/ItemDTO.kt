package br.com.unip.carrinho.dto

class ItemDTO {

    var produto: String
    var observacoes: String? = ""
    var quantidade: Long = 0

    constructor(produto: String, observacoes: String?, quantidade: Long) {
        this.produto = produto
        this.observacoes = observacoes
        this.quantidade = quantidade
    }
}