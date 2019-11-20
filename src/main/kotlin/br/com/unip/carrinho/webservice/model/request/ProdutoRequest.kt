package br.com.unip.carrinho.webservice.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProdutoRequest(@JsonProperty("id") val id: String?,
                     @JsonProperty("quantidade") val quantidade: Long?)