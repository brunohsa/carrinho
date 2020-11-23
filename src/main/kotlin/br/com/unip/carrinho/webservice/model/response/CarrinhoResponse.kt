package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class CarrinhoResponse {

    @JsonProperty(value = "id")
    val id: String?

    @JsonProperty(value = "fornecedor_uuid")
    val fornecedorUuid: String?

    @JsonProperty(value = "itens")
    val itens: List<ItemCarrinhoResponse>

    @JsonProperty(value = "valor_total")
    val valorTotal: Double

    @JsonProperty(value = "data_criacao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    var dataCriacao: LocalDateTime

    constructor(id: String?, fornecedorUuid: String?, itens: List<ItemCarrinhoResponse>, valorTotal: Double, dataCriacao: LocalDateTime) {
        this.fornecedorUuid = fornecedorUuid
        this.id = id
        this.itens = itens
        this.valorTotal = valorTotal
        this.dataCriacao = dataCriacao
    }
}