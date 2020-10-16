package br.com.unip.carrinho.dto

import java.math.BigDecimal
import java.time.LocalDateTime

class CarrinhoDTO(val id: String,
                  val produtos: List<ProdutoCarrinhoDTO>,
                  val valorTotal: BigDecimal,
                  val dataCriacao: LocalDateTime,
                  val fornecedorUUID: String?)

