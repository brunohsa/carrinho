package br.com.unip.carrinho.repository.entity

import java.math.BigDecimal
import java.time.LocalDateTime

class Pagamento {

    var nomeCompleto: String

    var numeroCartao: String

    var dataValidade: String

    var formaPagamento: String

    var valor: BigDecimal

    var dataPagamento = LocalDateTime.now()

    constructor(nomeCompleto: String, numeroCartao: String, dataValidade: String, formaPagamento: String,
                valor: BigDecimal) {
        this.nomeCompleto = nomeCompleto
        this.numeroCartao = numeroCartao
        this.dataValidade = dataValidade
        this.formaPagamento = formaPagamento
        this.valor = valor
    }
}