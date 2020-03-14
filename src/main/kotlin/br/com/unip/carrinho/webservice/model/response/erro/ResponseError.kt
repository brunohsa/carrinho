package br.com.unip.carrinho.webservice.model.response.erro

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseError {

    @JsonProperty("erro")
    var erro: Erro? = null

    constructor(erro: Erro?) {
        this.erro = erro
    }
}