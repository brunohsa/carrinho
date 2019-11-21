package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.Carrinho
import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param

interface ICarrinhoRepository : MongoRepository<Carrinho, String> {

    @Query("{ 'uuidCliente': ?0, 'status': ?1}")
    fun buscarCarrinho(@Param("uuidCliente") uuidCliente: String,
                       @Param("status") status: EStatusCarrinho = EStatusCarrinho.ATIVO): Carrinho?
}