package br.com.unip.carrinho.exception

import br.com.unip.carrinho.exception.ECodigoErro.PARAMETRO_INVALIDO
import org.springframework.http.HttpStatus.BAD_REQUEST

class ParametroInvalidoException : CarrinhoBaseException {

    constructor() : this(PARAMETRO_INVALIDO)

    constructor(codigoErro: ECodigoErro) : super(codigoErro, BAD_REQUEST)
}