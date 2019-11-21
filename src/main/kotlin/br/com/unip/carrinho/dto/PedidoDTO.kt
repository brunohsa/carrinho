package br.com.unip.carrinho.dto

class PedidoDTO {
    var id: String? = ""
    var uuidCliente: String? = ""
    var numero: Long? = 0
    var itens: List<ItemDTO>? = emptyList()
    var status: String? = ""

    constructor(id: String?, uuidCliente: String?, numero: Long?, itens: List<ItemDTO>?, status: String?) {
        this.id = id
        this.uuidCliente = uuidCliente
        this.numero = numero
        this.itens = itens
        this.status = status
    }
}