package br.com.unip.carrinho.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cardapio")
class Cardapio {

    @Id
    var id: String? = null

    var ativo: Boolean? = null

    var uuidFornecedor: String? = null
}