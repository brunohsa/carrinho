package br.com.unip.carrinho.repository.entity

import org.springframework.data.mongodb.core.mapping.DBRef

class ProdutoCarrinho {

    @DBRef
    lateinit var produto: Produto

    var observacoes: String? = ""

    var quantidade: Long = 0

    constructor()

    constructor(produto: Produto, observacoes: String?, quantidade: Long) {
        this.produto = produto
        this.observacoes = observacoes
        this.quantidade = quantidade
    }
}