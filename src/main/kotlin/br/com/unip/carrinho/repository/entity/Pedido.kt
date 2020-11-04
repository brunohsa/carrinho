package br.com.unip.carrinho.repository.entity

import br.com.unip.carrinho.repository.entity.enums.EStatusPedido
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "pedido")
class Pedido {

    @Id
    lateinit var id: String

    var fornecedorUUID: String

    var cadastroUUID: String

    var numero: String

    var itens: List<Item> = emptyList()

    var status: EStatusPedido = PENDENTE_PAGAMENTO

    var valor: Double

    lateinit var pagamento: Pagamento

    var dataPedido = LocalDateTime.now()

    var cliente: Cliente

    constructor(fornecedorUUID: String, cadastroUUID: String, numero: String, itens: List<Item>, valor: Double, cliente: Cliente) {
        this.fornecedorUUID = fornecedorUUID
        this.cadastroUUID = cadastroUUID
        this.numero = numero
        this.itens = itens
        this.valor = valor
        this.cliente = cliente
    }

    fun paraPendentePreparacao() {
        this.status = PENDENTE_PREPARACAO
    }

    fun paraConcluido() {
        this.status = CONCLUIDO
    }
}