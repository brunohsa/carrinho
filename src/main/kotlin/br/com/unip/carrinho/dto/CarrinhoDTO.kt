package br.com.unip.carrinho.dto

import java.time.LocalDateTime

class CarrinhoDTO(val id: String,
                  val produtos: List<ProdutoCarrinhoDTO>,
                  val valorTotal: Double,
                  val dataCriacao: LocalDateTime,
                  val fornecedorUUID: String?)

