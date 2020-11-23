package br.com.unip.carrinho.service

import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.Produto

interface IProdutoService {

    fun buscarProduto(id: String, cardapioId: String): Produto

    fun atualizarNotaMedia(id: String, notaMedia: Double)

    fun atualizarQuantidadeVendaEEstoque(pedido: Pedido)
}