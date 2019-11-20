package br.com.unip.carrinho.domain.campos

class CampoOpcional<T> : ICampo<T> {

    val valor: T

    constructor(valor: T) {
        this.valor = valor
    }

    override fun get(): T {
        return valor
    }
}