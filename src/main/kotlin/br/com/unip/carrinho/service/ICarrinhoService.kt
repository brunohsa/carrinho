package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.CarrinhoDTO

interface ICarrinhoService {

    fun criar(): String

    fun buscar(): CarrinhoDTO

    fun adicionarProduto(dto: AdicionarProdutoCarrinhoDTO): CarrinhoDTO

    fun removerProduto(idProduto: String)

    fun finalizar(id: String)
}