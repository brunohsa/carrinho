package br.com.unip.carrinho.domain.campos

class Nome : ICampo<String> {

    val nome: String

    constructor(nome: String?) {
        this.nome = CampoObrigatorio(nome).get()
    }

    override fun get(): String {
        return nome
    }
}