package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.ProdutoDTO
import br.com.unip.carrinho.repository.entity.Produto

interface IProdutoService {

    fun buscar(id: String): ProdutoDTO

    fun buscarProduto(id: String): Produto
}