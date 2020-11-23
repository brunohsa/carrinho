package br.com.unip.carrinho.service

import br.com.unip.carrinho.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.Produto
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class ProdutoService(val mongoTemplate: MongoTemplate) : IProdutoService {

    override fun buscarProduto(id: String, cardapioId: String): Produto {
        val criteria = Criteria.where("_id").`is`(ObjectId(id)).and("cardapioId").`is`(cardapioId)
        val query = Query().addCriteria(criteria)
        return mongoTemplate.findOne(query, Produto::class.java) ?: throw NaoEncontradoException(PRODUTO_NAO_ENCONTRADO)
    }

    private fun buscar(id: String): Produto {
        val criteria = Criteria.where("_id").`is`(ObjectId(id))
        val query = Query().addCriteria(criteria)
        return mongoTemplate.findOne(query, Produto::class.java) ?: throw NaoEncontradoException(PRODUTO_NAO_ENCONTRADO)
    }

    override fun atualizarNotaMedia(id: String, notaMedia: Double) {
        val produto = this.buscar(id)
        produto.nota = notaMedia
        mongoTemplate.save(produto)
    }

    override fun atualizarQuantidadeVendaEEstoque(pedido: Pedido) {
        pedido.itens.map { item ->
            val produto = this.buscar(item.id)
            produto.vendidos += item.quantidade
            produto.estoque -= item.quantidade
            mongoTemplate.save(produto)
        }
    }
}