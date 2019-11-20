package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus

class DataPassadaException : CadastroException {

    constructor() : super(ECodigoErro.CAD013, HttpStatus.BAD_REQUEST)

    constructor(mensagem: String) : this(mensagem, ECodigoErro.CAD013)

    constructor(mensagem: String, codigoErro: ECodigoErro) : super(codigoErro, HttpStatus.BAD_REQUEST, mensagem)
}