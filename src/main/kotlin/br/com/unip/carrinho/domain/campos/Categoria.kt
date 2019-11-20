package br.com.unip.carrinho.domain.campos

import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho

class Categoria : ICampo<EStatusCarrinho> {

    val valor: EStatusCarrinho

    constructor(valor: String?) {
        this.valor = EStatusCarrinho.valueOf(CampoObrigatorio(valor).get())
    }

    override fun get(): EStatusCarrinho {
        return valor
    }
}