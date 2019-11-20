package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.CarrinhoDTO
import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO

interface ICarrinhoService {

    fun criar(): String

    fun adicionarProduto(idCarrinho: String?, dto: AdicionarProdutoCarrinhoDTO): CarrinhoDTO
}