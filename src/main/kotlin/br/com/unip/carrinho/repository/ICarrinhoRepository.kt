package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.Carrinho
import org.springframework.data.mongodb.repository.MongoRepository

interface ICarrinhoRepository : MongoRepository<Carrinho, String>