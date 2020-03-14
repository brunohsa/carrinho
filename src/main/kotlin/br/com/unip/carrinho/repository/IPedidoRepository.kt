package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido.CONCLUIDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface IPedidoRepository : MongoRepository<Pedido, String> {

    @Query("{ 'uuidCliente': ?0, 'status': {`$`not: ?1} }")
    fun buscarPedidosPorUuidCadastro(uuid: String, status: EStatusPedido = CONCLUIDO): List<Pedido>

    @Query("{cadastroUUID: ?0, status: {`$`in: ?1} }")
    fun buscarPedidos(uuid: String, status: List<EStatusPedido>) : List<Pedido>
}