package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

class AvaliacaoProdutoResponse {

    @JsonProperty("usuarioUUID")
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