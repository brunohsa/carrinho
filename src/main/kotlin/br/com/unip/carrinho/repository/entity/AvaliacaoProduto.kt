package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "avaliacaoProduto")
class AvaliacaoProduto {

    @Id
    lateinit var id: String

    var usuarioUUID: String

    var pedidoId: String

    var produtoId: String

    var nota: Long

    constructor(usuarioUUID: String, pedidoId: String, produtoId: String, nota: Long) {
        this.usuarioUUID = usuarioUUID
        this.pedidoId = pedidoId
        this.produtoId = produtoId
        this.nota = nota
    }
}