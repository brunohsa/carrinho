package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class ClienteResponse(@JsonProperty("nome")
                      val nome: String,

                      @JsonProperty("telefone")
                      val telefone: String?)