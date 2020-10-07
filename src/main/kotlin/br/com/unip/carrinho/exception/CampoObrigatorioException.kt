package br.com.unip.carrinho.exception

import br.com.unip.carrinho.exception.ECodigoErro.CAMPO_OBRIGATORIO
import org.springframework.http.HttpStatus.BAD_REQUEST

class CampoObrigatorioException : CarrinhoBaseException {

    constructor() : this(CAMPO_OBRIGATORIO)

    constructor(codigoErro: ECodigoErro) : super(codigoErro, BAD_REQUEST)
}