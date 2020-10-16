package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "produto")
class Produto {

    @Id
    lateinit var id: String

    var nome: String

    var valor: BigDecimal

    var cardapioId: String

    constructor(nome: String, valor: BigDecimal, cardapioId: String) {
        this.nome = nome
        this.valor = valor
        this.cardapioId = cardapioId
    }
}