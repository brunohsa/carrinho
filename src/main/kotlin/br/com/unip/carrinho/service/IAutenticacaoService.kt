package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.CadastroDTO


interface IAutenticacaoService {

    fun buscarCadastroPorEmail(email: String): CadastroDTO
}