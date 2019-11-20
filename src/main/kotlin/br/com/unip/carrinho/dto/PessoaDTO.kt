package br.com.unip.carrinho.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class PessoaDTO(@JsonProperty(value = "nome") val nome: String,
                @JsonProperty(value = "tipo_documento") var tipoDocumento: String,
                @JsonProperty(value = "numero_documento") var numero: String)
