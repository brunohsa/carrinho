package br.com.unip.carrinho.webservice.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class AvaliarPedidoRequest(@JsonProperty("nota") var nota: Long?,
                           @JsonProperty("comentario") var comentario: String?)