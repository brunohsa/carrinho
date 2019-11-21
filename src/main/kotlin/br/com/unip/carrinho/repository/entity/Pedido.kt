package br.com.unip.carrinho.repository.entity

import br.com.unip.carrinho.repository.entity.enums.EStatusPedido
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "pedido")
class Pedido {

    @Id
    var id: String? = null

    var uuidCliente:String? = null

    var numero: Long? = 0

    var itens: List<Item>? = emptyList()

    var status: EStatusPedido? = EStatusPedido.PENDENTE

    constructor(uuidCliente: String?, numero: Long?, itens: List<Item>?) {
        this.uuidCliente = uuidCliente
        this.numero = numero
        this.itens = itens
    }
}