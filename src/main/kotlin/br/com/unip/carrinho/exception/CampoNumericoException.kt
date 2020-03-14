package br.com.unip.carrinho.exception

import br.com.unip.carrinho.exception.ECodigoErro.CAMPO_DEVE_SER_NUMERICO
import org.springframework.http.HttpStatus.BAD_REQUEST

class CampoNumericoException : CarrinhoBaseException {

    constructor() : this(CAMPO_DEVE_SER_NUMERICO)

    constructor(codigoErro: ECodigoErro) : super(codigoErro, BAD_REQUEST)
}