package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class CarrinhoResponse {

    @JsonProperty(value = "id")
    val id: String?

    @JsonProperty(value = "itens")
    val itens: List<ItemCarrinhoResponse>

    @JsonProperty(value = "valor_total")
    val valorTotal: BigDecimal

    @JsonProperty(value = "data_criacao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    var dataCriacao: LocalDateTime

    constructor(id: String?, itens: List<ItemCarrinhoResponse>, valorTotal: BigDecimal, dataCriacao: LocalDateTime) {
        this.id = id
        this.itens = itens
        this.valorTotal = valorTotal
        this.dataCriacao = dataCriacao
    }
}