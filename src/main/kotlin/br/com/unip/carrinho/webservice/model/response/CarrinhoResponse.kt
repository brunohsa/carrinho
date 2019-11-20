package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
class CarrinhoResponse {

    @JsonProperty(value = "id")
    val id: String?

    @JsonProperty(value = "itens")
    val itens: List<ItemCarrinhoResponse>

    @JsonProperty(value = "valor_total")
    val valorTotal: BigDecimal?

    constructor(id: String?, itens: List<ItemCarrinhoResponse>, valorTotal: BigDecimal?) {
        this.id = id
        this.itens = itens
        this.valorTotal = valorTotal
    }
}