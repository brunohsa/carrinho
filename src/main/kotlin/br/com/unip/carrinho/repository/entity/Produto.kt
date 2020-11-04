package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "produto")
class Produto {

    @Id
    lateinit var id: String

    var nome: String

    var valor: Double

    var cardapioId: String

    var vendidos: Long = 0

    var nota: Double = 0.0

    constructor(nome: String, valor: Double, cardapioId: String) {
        this.nome = nome
        this.valor = valor
        this.cardapioId = cardapioId
    }
}