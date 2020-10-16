package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.PessoaFisicaDTO

interface ICadastroService {

    fun buscarPessoaFisica(cadastroUUID: String): PessoaFisicaDTO
}