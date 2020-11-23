package br.com.unip.carrinho.service

import br.com.unip.carrinho.repository.entity.Cardapio

interface ICardapioService {

    fun buscar(cardapioId: String) : Cardapio
}