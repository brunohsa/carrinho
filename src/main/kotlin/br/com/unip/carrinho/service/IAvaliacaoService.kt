package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.AvaliarPedidoDTO
import br.com.unip.carrinho.dto.AvaliarProdutoDTO

interface IAvaliacaoService {

    fun avaliarPedido(dto: AvaliarPedidoDTO)

    fun avaliarProduto(dto: AvaliarProdutoDTO)
}