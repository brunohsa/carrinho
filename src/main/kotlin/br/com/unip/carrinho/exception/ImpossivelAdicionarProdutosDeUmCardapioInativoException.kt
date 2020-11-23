package br.com.unip.carrinho.exception

import br.com.unip.carrinho.exception.ECodigoErro.CLIENTE_JA_POSSUI_UM_CARRINHO
import org.springframework.http.HttpStatus

class ImpossivelAdicionarProdutosDeUmCardapioInativoException : CarrinhoBaseException {

    constructor() : super(ECodigoErro.IMPOSSIVEL_ADICIONAR_PRODUTO_DE_CARDAPIO_INATIVO, HttpStatus.BAD_REQUEST)
}