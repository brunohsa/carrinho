package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface IPedidoRepository : MongoRepository<Pedido, String> {

    @Query("{ 'uuidCliente': ?0, 'status': {`$`not: ?1} }")
    fun buscarPedidosPorUuidCadastro(uuid: String, status: EStatusPedido = EStatusPedido.CONCLUIDO): List<Pedido>
}