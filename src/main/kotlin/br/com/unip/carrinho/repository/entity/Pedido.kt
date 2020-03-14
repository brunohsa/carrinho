package br.com.unip.carrinho.repository.entity

import br.com.unip.carrinho.repository.entity.enums.EStatusPedido
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido.CONCLUIDO
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido.PENDENTE_PAGAMENTO
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido.PENDENTE_PREPARACAO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "pedido")
class Pedido {

    @Id
    lateinit var id: String

    var cadastroUUID: String

    var numero: String

    var itens: List<Item> = emptyList()

    var status: EStatusPedido = PENDENTE_PAGAMENTO

    var valor: BigDecimal

    lateinit var pagamento: Pagamento

    var dataPedido = LocalDateTime.now()

    constructor(cadastroUUID: String, numero: String, itens: List<Item>, valor: BigDecimal) {
        this.cadastroUUID = cadastroUUID
        this.numero = numero
        this.itens = itens
        this.valor = valor
    }

    fun paraPendentePreparacao() {
        this.status = PENDENTE_PREPARACAO
    }

    fun paraConcluido() {
        this.status = CONCLUIDO
    }
}