package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.repository.entity.Pedido

interface IPedidoConsumidorService {

    fun gerar(): PedidoDTO

    fun buscarPedidos(filtro: FiltroPedidoDTO): List<PedidoDTO>

    fun buscarPedido(id: String): Pedido

    fun pagar(id: String, dadosPagamento: DadosPagamentoDTO): PedidoDTO
}