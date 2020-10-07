package br.com.unip.carrinho.repository.entity

import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "carrinho")
class Carrinho {

    @Id
    lateinit var id: String

    lateinit var cadastroUUID: String

    var produtos: List<ProdutoCarrinho> = emptyList()

    lateinit var status: EStatusCarrinho

    var dataCriacao: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(cadastroUuid: String, produtos: List<ProdutoCarrinho>, status: EStatusCarrinho)
            : this(cadastroUuid, status) {
        this.id = id
        this.produtos = produtos
    }

    constructor(cadastroUuid: String, status: EStatusCarrinho) {
        this.cadastroUUID = cadastroUuid
        this.status = status
    }

    fun removerProduto(produtoCarrinho: ProdutoCarrinho) {
        val produtos = this.produtos.toMutableList()
        produtos.remove(produtoCarrinho)
        this.produtos = produtos
    }
}