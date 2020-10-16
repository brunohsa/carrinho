package br.com.unip.carrinho.dto

import java.math.BigDecimal

class ItemDTO(val id: String, val nome: String, val observacoes: String?, val quantidade: Long, val valor: BigDecimal)