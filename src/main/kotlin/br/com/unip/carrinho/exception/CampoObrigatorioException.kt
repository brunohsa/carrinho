package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus

class CampoObrigatorioException : CadastroException {


    constructor() : super(ECodigoErro.CAD002, HttpStatus.BAD_REQUEST)

    constructor(mensagem: String) : this(mensagem, ECodigoErro.CAD002)

    constructor(mensagem: String, codigoErro: ECodigoErro) : super(codigoErro, HttpStatus.BAD_REQUEST, mensagem)
}