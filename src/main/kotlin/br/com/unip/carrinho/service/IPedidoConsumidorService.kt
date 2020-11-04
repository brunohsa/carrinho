package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.PedidoDTO

interface IPedidoConsumidorService {

    fun gerar(): PedidoDTO

    fun buscarPedidos(filtro: FiltroPedidoDTO): List<PedidoDTO>

    fun pagar(id: String, dadosPagamento: DadosPagamentoDTO): PedidoDTO
}