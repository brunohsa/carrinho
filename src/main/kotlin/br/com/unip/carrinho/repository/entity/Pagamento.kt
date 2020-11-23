package br.com.unip.carrinho.repository.entity

import java.time.LocalDateTime

class Pagamento {

    var nomeCompleto: String

    var numeroCartao: String

    var dataValidade: String

    var formaPagamento: String

    var valor: Double

    var dataPagamento = LocalDateTime.now()

    constructor(nomeCompleto: String, numeroCartao: String, dataValidade: String, formaPagamento: String,
                valor: Double) {
        this.nomeCompleto = nomeCompleto
        this.numeroCartao = numeroCartao
        this.dataValidade = dataValidade
        this.formaPagamento = formaPagamento
        this.valor = valor
    }
}