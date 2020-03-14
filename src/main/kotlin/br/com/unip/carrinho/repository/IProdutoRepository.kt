package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.Produto
import org.springframework.data.mongodb.repository.MongoRepository

interface IProdutoRepository : MongoRepository<Produto, String>