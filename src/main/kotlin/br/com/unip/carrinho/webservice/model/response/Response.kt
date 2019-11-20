package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class Response {

    @JsonProperty("payload")
    var payload: Any? = null

    @JsonProperty("erro")
    var erro: Erro? = null

    constructor(payload: Any?) {
        this.payload = payload
    }

    constructor(erro: Erro?) {
        this.erro = erro
    }
}