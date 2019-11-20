package br.com.unip.carrinho.webservice.model.response

import br.com.unip.carrinho.exception.ECodigoErro
import com.fasterxml.jackson.annotation.JsonProperty

class Erro(@JsonProperty("codigo") val codigoErro: ECodigoErro,
           @JsonProperty("mensagem") val mensagem: String)