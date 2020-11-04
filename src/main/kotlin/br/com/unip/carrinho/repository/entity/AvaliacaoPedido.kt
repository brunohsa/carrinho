package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "avaliacaoPedido")
class AvaliacaoPedido {

    @Id
    lateinit var id: String

    var usuarioUUID: String

    var fornecedorUUID: String

    var pedidoId: String

    var nota: Long

    var comentario: String?

    constructor(usuarioUUID: String, fornecedorUUID: String,  pedidoId: String, nota: Long, comentario: String?) {
        this.usuarioUUID = usuarioUUID
        this.fornecedorUUID = fornecedorUUID
        this.pedidoId = pedidoId
        this.nota = nota
        this.comentario = comentario
    }
}