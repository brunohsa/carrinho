package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus

class ClienteJaPossuiCarrinhoAtivoException : CadastroException {

    constructor() : super(ECodigoErro.CAD019, HttpStatus.BAD_REQUEST, "Cliente já possui carrinho.")
}