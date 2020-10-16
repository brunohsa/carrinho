package br.com.unip.carrinho.dto

import java.math.BigDecimal
import java.time.LocalDateTime

class PedidoDTO(val id: String,
                val numero: String,
                var itens: List<ItemDTO> = emptyList(),
                val status: String,
                val valor: BigDecimal,
                val cliente: ClienteDTO,
                val dataPedido: LocalDateTime)