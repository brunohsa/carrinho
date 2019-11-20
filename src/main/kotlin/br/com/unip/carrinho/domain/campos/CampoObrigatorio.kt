package br.com.unip.carrinho.domain.campos

import br.com.unip.carrinho.exception.CampoObrigatorioException
import org.springframework.util.StringUtils

class CampoObrigatorio<T> : ICampo<T> {

    val valor: T

    constructor(valor: T?) {
        if (StringUtils.isEmpty(valor)) {
            throw CampoObrigatorioException()
        }
        this.valor = valor!!
    }

    override fun get(): T {
        return valor
    }
}