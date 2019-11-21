package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

class ItemResponse {
    @JsonProperty("produto")
    var produto: ProdutoResponse? = null

    @JsonProperty("observacoes")
    var observacoes: String? = ""

    @JsonProperty("quantidade")
    var quantidade: Long? = 0

    @JsonProperty("cliente")
    var cliente: String? = ""

    constructor(produto: ProdutoResponse?, observacoes: String?, quantidade: Long?, cliente: String?) {
        this.produto = produto
        this.observacoes = observacoes
        this.quantidade = quantidade
        this.cliente = cliente
    }
}