package br.com.unip.carrinho.dto

class ItemDTO(val id: String, val nome: String, val observacoes: String?, val quantidade: Int, val valor: Double) {

    var avaliacao: AvaliacaoProdutoDTO? = null
}