package br.com.unip.carrinho.exception

import br.com.unip.carrinho.exception.ECodigoErro.CLIENTE_JA_POSSUI_UM_CARRINHO
import org.springframework.http.HttpStatus

class ClienteJaPossuiCarrinhoAtivoException : CarrinhoBaseException {

    constructor() : super(CLIENTE_JA_POSSUI_UM_CARRINHO, HttpStatus.BAD_REQUEST)
}