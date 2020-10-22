package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.PedidoDTO

interface IPedidoFornecedorService {

    fun buscarPedidos(filtro: FiltroPedidoDTO): List<PedidoDTO>

    fun concluido(pedidoId: String)
}