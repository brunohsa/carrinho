package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus.BAD_REQUEST

class CarrinhoPossuiProdutosDeOutroFornecedorException : CarrinhoBaseException {

    constructor() : super(ECodigoErro.CARRINHO_POSSUI_PRODUTOS_DE_OUTRO_FORNECEDOR, BAD_REQUEST)
}