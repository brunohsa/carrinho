package br.com.unip.carrinho.domain.campos

class CampoTamanhoLimite : ICampo<String> {

    private val campo: ICampo<String>

    constructor(campo: ICampo<String>, maxSize: Int) {
        if (campo.get().length > maxSize) {
            throw RuntimeException()
        }
        this.campo = campo
    }

    override fun get(): String {
        return campo.get()
    }
}