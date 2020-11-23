package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus

class ProdutoIndisponivelException : CarrinhoBaseException {

    constructor() : super(ECodigoErro.IMPOSSIVEL_ADICIONAR_PRODUTO_DE_CARDAPIO_INATIVO, HttpStatus.BAD_REQUEST)
}