package br.com.unip.carrinho.dto

class DadosPagamentoDTO {

    var nomeCompleto: String?
    var numeroCartao: String?
    var dataValidade: String?
    var codigo: String?
    var formaPagamento: String?
    var salvar: Boolean = false

    constructor(nomeCompleto: String?, numeroCartao: String?, dataValidade: String?, codigo: String?,
                formaPagamento: String?, salvar: Boolean) {
        this.nomeCompleto = nomeCompleto
        this.numeroCartao = numeroCartao
        this.dataValidade = dataValidade
        this.codigo = codigo
        this.formaPagamento = formaPagamento
        this.salvar = salvar
    }
}