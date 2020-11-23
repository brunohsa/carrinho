package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.CadastroDTO

interface ICadastroService {

    fun buscarCadastro(cadastroUUID: String): CadastroDTO

    fun atualizarNotaMediaFornecedor(fornecedorUUID: String, nota: Double)
}