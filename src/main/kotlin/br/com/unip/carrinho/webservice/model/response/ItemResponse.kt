package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

class ItemResponse {

    @JsonProperty("produto")
    var produto: String

    @JsonProperty("observacoes")
    var observacoes: String? = ""

    @JsonProperty("quantidade")
    var quantidade: Long = 0

    constructor(produto: String, observacoes: String?, quantidade: Long) {
        this.produto = produto
        this.observacoes = observacoes
        this.quantidade = quantidade
    }
}