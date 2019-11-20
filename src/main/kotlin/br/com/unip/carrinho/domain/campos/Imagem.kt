package br.com.unip.carrinho.domain.campos

class Imagem : ICampo<String> {

    val valor: String

    constructor(valor: String?) {
        this.valor = CampoObrigatorio(valor).get()
    }

    override fun get(): String {
        return valor
    }
}