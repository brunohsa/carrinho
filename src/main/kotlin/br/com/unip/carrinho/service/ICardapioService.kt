package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.ProdutoCardapioDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoDTO

interface ICardapioService {

    fun buscarProduto(idProduto: String) : ProdutoCardapioDTO
}