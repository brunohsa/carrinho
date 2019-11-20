package br.com.unip.carrinho.repository.entity

import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "carrinho")
class Carrinho {

    @Id
    var id: String? = null

    var uuidCliente: String? = null

    var produtos: List<ProdutoCarrinho>? = emptyList()

    var status: EStatusCarrinho? = null

    constructor()

    constructor(uuidCliente: String?, produtos: List<ProdutoCarrinho>?, status: EStatusCarrinho?) {
        this.id = id
        this.uuidCliente = uuidCliente
        this.status = status
        this.produtos = produtos
    }

    constructor(uuidCliente: String?, status: EStatusCarrinho?) {
        this.uuidCliente = uuidCliente
        this.status = status
    }
}