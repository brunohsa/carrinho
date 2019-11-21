package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sequence")
class SequenceId {

    @Id
    var id: String? = null

    var seq: Long = 0
}