package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class PedidoResponse {

    @JsonProperty("id")
    var id: String?

    @JsonProperty("numero")
    var numero: String

    @JsonProperty("itens")
    var itens: List<ItemResponse> = emptyList()

    @JsonProperty("valor_total")
    var valor: BigDecimal

    constructor(id: String?, numero: String, itens: List<ItemResponse>, valor: BigDecimal) {
        this.id = id
        this.numero = numero
        this.itens = itens
        this.valor = valor
    }
}