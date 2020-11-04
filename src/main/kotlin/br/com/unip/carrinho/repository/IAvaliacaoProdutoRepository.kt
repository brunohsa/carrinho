package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.AvaliacaoProduto
import org.springframework.data.mongodb.repository.MongoRepository

interface IAvaliacaoProdutoRepository : MongoRepository<AvaliacaoProduto, String> {


}