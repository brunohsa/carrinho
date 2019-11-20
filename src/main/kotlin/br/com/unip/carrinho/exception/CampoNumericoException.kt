package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus

class CampoNumericoException : CadastroException {

    constructor() : super(ECodigoErro.CAD007, HttpStatus.BAD_REQUEST)

    constructor(mensagem: String) : this(mensagem, ECodigoErro.CAD007)

    constructor(mensagem: String, codigoErro: ECodigoErro) : super(codigoErro, HttpStatus.BAD_REQUEST, mensagem)
}