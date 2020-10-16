package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.PedidoDTO

interface IPedidoService {

    fun gerar(): PedidoDTO

    fun buscarPedidosConsumidor(filtro: FiltroPedidoDTO): List<PedidoDTO>

    fun buscarPedidosFornecedor(filtro: FiltroPedidoDTO): List<PedidoDTO>

    fun concluido(pedidoId: String)

    fun pagar(id: String, dadosPagamento: DadosPagamentoDTO)
}