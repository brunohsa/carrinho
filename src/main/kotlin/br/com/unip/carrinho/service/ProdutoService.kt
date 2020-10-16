package br.com.unip.carrinho.service

import br.com.unip.carrinho.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
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
}