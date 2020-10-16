package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

class PedidoResponse {

    @JsonProperty("id")
    val id: String?

    @JsonProperty("numero")
    val numero: String

    @JsonProperty("status")
    val status: String

    @JsonProperty("itens")
    val itens: List<ItemResponse>

    @JsonProperty("valor_total")
    val valor: BigDecimal

    @JsonProperty("cliente")
    var cliente: ClienteResponse? = null

    @JsonProperty("data_pedido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    var dataPedido: LocalDateTime

    constructor(id: String?, numero: String, status: String, itens: List<ItemResponse>, valor: BigDecimal,
                cliente: ClienteResponse?, dataPedido: LocalDateTime)
            : this(id, numero, status, itens, valor, dataPedido) {
        this.cliente = cliente
    }

    constructor(id: String?, numero: String, status: String, itens: List<ItemResponse>, valor: BigDecimal,
                dataPedido: LocalDateTime) {
        this.id = id
        this.numero = numero
        this.status = status
        this.itens = itens
        this.valor = valor
        this.dataPedido = dataPedido
    }
}