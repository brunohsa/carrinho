package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido

interface IPedidoService {

    fun gerar(): PedidoDTO

    fun buscarPedidos(status: List<String>): List<PedidoDTO>

    fun concluido(pedidoId: String)

    fun pagar(id: String, dadosPagamento: DadosPagamentoDTO)
}