package br.com.unip.carrinho.webservice.model.response.erro

import br.com.unip.carrinho.webservice.model.response.erro.EMicroservice.CARRINHO
import com.fasterxml.jackson.annotation.JsonProperty

class Erro {

    @JsonProperty(value = "codigo")
    lateinit var codigoErro: String

    @JsonProperty(value = "mensagem")
    var mensagem: String? = ""

    @JsonProperty(value = "microservico")
    var microservice: EMicroservice = CARRINHO

    constructor()

    constructor(codigo: String, mensagem: String?) {
        this.codigoErro = codigo
        this.mensagem = mensagem
    }
}