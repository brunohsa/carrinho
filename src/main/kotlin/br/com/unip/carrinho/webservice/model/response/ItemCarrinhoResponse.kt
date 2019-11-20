package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ItemCarrinhoResponse {

    @JsonProperty(value = "produto")
    var produto: ProdutoResponse?

    @JsonProperty(value = "quantidade")
    var quantidade: Long?

    constructor(produto: ProdutoResponse?, quantidade: Long?) {
        this.produto = produto
        this.quantidade = quantidade
    }
}