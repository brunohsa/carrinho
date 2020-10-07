package br.com.unip.carrinho.webservice.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProdutoRequest(@JsonProperty("quantidade") var quantidade: Long = 1,
                     @JsonProperty("observacoes") var observacoes: String?)