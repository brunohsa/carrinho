package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus

class ClienteNaoPossuiCarrinhoException : CadastroException {

    constructor() : super(ECodigoErro.CAD018, HttpStatus.NOT_FOUND, "Cliente não possui carrinho.")
}