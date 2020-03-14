package br.com.unip.carrinho.dto

import java.math.BigDecimal

class PedidoDTO {

    var id: String
    var numero: String
    var itens: List<ItemDTO> = emptyList()
    var status: String
    var valor: BigDecimal

    constructor(id: String, numero: String, itens: List<ItemDTO>, status: String, valor: BigDecimal) {
        this.id = id
        this.numero = numero
        this.itens = itens
        this.status = status
        this.valor = valor
    }
}