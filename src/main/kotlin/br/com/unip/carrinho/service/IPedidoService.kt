package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.PedidoDTO

interface IPedidoService {

    fun gerar(): String

    fun buscarPedidos(): List<PedidoDTO>

    fun concluido(pedidoId: String)

    fun preparando(pedidoId: String)
}