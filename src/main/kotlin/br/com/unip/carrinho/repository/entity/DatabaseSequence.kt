package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "database_sequences")
class DatabaseSequence {

    @Id
    var id: String? = null

    var seq: Long = 0

    var dataRegistro: LocalDate = LocalDate.now()

    constructor(id: String?) {
        this.id = id
    }
}