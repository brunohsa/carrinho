package br.com.unip.carrinho.webservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class ItemResponse(@JsonProperty("id")
                   val id: String,

                   @JsonProperty("nome")
                   val nome: String,

                   @JsonProperty("observacoes")
                   val observacoes: String?,

                   @JsonProperty("quantidade")
                   val quantidade: Long,

                   @JsonProperty("valor")
                   val valor: BigDecimal)