package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus

class ParametroInvalidoException : CadastroException {

    constructor(mensagem: String) : this(mensagem, ECodigoErro.CAD001)

    constructor(mensagem: String, codigoErro: ECodigoErro) : super(codigoErro, HttpStatus.BAD_REQUEST, mensagem)
}