package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus.NOT_FOUND

class NaoEncontradoException : CarrinhoBaseException {

    constructor(erro: ECodigoErro) : super(erro, NOT_FOUND)
}