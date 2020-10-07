package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
class CarrinhoCriadoResponse {

    @JsonProperty(value = "carrinho_id")
    val id: String?

    constructor(id: String?) {
        this.id = id
    }
}