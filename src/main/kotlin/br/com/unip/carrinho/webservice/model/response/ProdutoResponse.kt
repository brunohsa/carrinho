package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProdutoResponse {

    @JsonProperty(value = "id")
    var id: String?

    @JsonProperty(value = "nome")
    var nome: String?

    @JsonProperty(value = "valor")
    var valor: String?

    constructor(id: String?, nome: String?, valor: String?) {
        this.id = id
        this.nome = nome
        this.valor = valor
    }
}