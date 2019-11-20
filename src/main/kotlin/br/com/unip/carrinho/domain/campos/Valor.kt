package br.com.unip.carrinho.domain.campos

import java.math.BigDecimal

class Valor : ICampo<BigDecimal> {

    val valor: BigDecimal

    constructor(valor: String?) {
        this.valor = CampoMonetario(CampoObrigatorio(valor)).get()
    }

    override fun get(): BigDecimal {
        return valor
    }
}