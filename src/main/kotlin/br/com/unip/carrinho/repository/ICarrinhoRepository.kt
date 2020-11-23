package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.Carrinho
import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ICarrinhoRepository : MongoRepository<Carrinho, String> {

    @Query("{ 'cadastroUUID': ?0, 'status': ?1}")
    fun buscarCarrinho(@Param("cadastroUUID") cadastroUuid: String,
                       @Param("status") status: EStatusCarrinho = EStatusCarrinho.ATIVO): Optional<Carrinho>

    fun findByFornecedorUUIDAndStatus(fornecedorUuid: String, status: EStatusCarrinho = EStatusCarrinho.ATIVO): List<Carrinho>
}