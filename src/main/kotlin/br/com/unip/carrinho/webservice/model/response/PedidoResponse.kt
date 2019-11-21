package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

class PedidoResponse {

    @JsonProperty("id")
    var id: String?

    @JsonProperty("numero")
    var numero: Long?

    @JsonProperty("itens")
    var itens: List<ItemResponse>? = emptyList()

    constructor(id: String?, numero: Long?, itens: List<ItemResponse>?) {
        this.id = id
        this.numero = numero
        this.itens = itens
    }
}