package br.com.unip.carrinho.repository.entity

import java.math.BigDecimal

class Item(val id: String, val produto: String, val observacoes: String?, val quantidade: Long, val valor: BigDecimal)