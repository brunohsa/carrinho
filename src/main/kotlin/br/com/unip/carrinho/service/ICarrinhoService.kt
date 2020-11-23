package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.CarrinhoDTO

interface ICarrinhoService {

    fun criar(): CarrinhoDTO

    fun buscar(): CarrinhoDTO

    fun buscarPorFornecedor(uuidFornecedor: String): List<CarrinhoDTO>

    fun adicionarProduto(dto: AdicionarProdutoCarrinhoDTO, cardapioId: String): CarrinhoDTO

    fun removerProduto(idProduto: String)

    fun finalizar(id: String)
}