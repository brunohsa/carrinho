package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.AvaliacaoPedido
import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.Produto
import org.springframework.data.mongodb.repository.MongoRepository

interface IAvaliacaoPedidoRepository : MongoRepository<AvaliacaoPedido, String> {

    fun findByusuarioUUIDAndPedidoId(usuarioUUID: String, pedidoId: String): AvaliacaoPedido?

}