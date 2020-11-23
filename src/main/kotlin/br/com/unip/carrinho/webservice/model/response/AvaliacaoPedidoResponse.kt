package br.com.unip.carrinho.webservice.model.response

class AvaliacaoPedidoResponse {

    var pedidoId: String
    var nota: Long? = null
    var comentario: String? = null

    constructor(pedidoId: String, nota: Long?, comentario: String?) {
        this.pedidoId = pedidoId
        this.nota = nota
        this.comentario = comentario
    }
}