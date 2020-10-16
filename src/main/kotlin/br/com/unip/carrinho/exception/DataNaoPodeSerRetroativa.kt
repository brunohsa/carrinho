package br.com.unip.carrinho.exception

import br.com.unip.carrinho.exception.ECodigoErro.DATA_NAO_DEVE_SER_RETROATIVA
import org.springframework.http.HttpStatus.BAD_REQUEST

class DataNaoPodeSerRetroativa : CarrinhoBaseException {

    constructor() : this(DATA_NAO_DEVE_SER_RETROATIVA)

    constructor(codigoErro: ECodigoErro) : super(codigoErro, BAD_REQUEST)
}