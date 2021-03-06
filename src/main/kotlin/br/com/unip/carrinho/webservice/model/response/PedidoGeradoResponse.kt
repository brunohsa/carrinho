package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class PedidoGeradoResponse {

    @JsonProperty(value = "numero")
    val id: String?

    constructor(id: String?) {
        this.id = id
    }
}