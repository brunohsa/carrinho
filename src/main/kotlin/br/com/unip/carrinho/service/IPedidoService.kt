package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.repository.entity.Pedido

interface IPedidoService {

    fun buscarPedidos(filtro: FiltroPedidoDTO, uuidCadastro: String, keyWhere: String): List<PedidoDTO>

    fun buscarPedido(id: String): Pedido
}