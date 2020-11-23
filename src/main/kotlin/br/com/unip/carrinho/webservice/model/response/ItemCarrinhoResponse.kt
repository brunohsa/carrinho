package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ItemCarrinhoResponse {

    @JsonProperty(value = "produto")
    var produto: ProdutoResponse?

    @JsonProperty(value = "quantidade")
    var quantidade: Int?

    @JsonProperty(value = "observacoes")
    var observacoes: String? = ""

    constructor(produto: ProdutoResponse?, quantidade: Int?, observacoes: String?) {
        this.produto = produto
        this.quantidade = quantidade
        this.observacoes = observacoes
    }
}