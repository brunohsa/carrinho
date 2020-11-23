package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.AvaliacaoProdutoDTO
import br.com.unip.carrinho.dto.AvaliarPedidoDTO
import br.com.unip.carrinho.dto.AvaliarProdutoDTO
import br.com.unip.carrinho.repository.entity.AvaliacaoPedido
import br.com.unip.carrinho.repository.entity.AvaliacaoProduto

interface IAvaliacaoService {

    fun avaliarPedido(dto: AvaliarPedidoDTO)

    fun avaliarProduto(dto: AvaliarProdutoDTO)

    fun buscarAvaliacaoPedido(clienteUUID: String, pedidoId: String): AvaliarPedidoDTO?

    fun buscarAvaliacaoProdutos(clienteUUID: String, pedidoId: String): List<AvaliacaoProdutoDTO>

    fun buscarAvaliacaoProduto(clienteUUID: String, pedidoId: String, produtoId: String): AvaliacaoProdutoDTO?
}