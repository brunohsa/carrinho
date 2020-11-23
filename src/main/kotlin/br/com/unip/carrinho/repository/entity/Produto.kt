package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "produto")
class Produto {

    @Id
    var id: String = ""

    var nome: String = ""

    var descricao: String? = null

    var valor: Double = 0.0

    var urlImagem: String? = null

    var categoriaId: String? = null

    var subcategoriaId: String? = null

    var cardapioId: String? = null

    var estoque: Int = 0

    var vendidos: Int = 0

    var nota: Double? = null

    constructor(nome: String, valor: Double, cardapioId: String) {
        this.nome = nome
        this.valor = valor
        this.cardapioId = cardapioId
    }
}