package br.com.unip.carrinho.webservice.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class DadosPagamentoRequest(@JsonProperty("nome_completo") var nomeCompleto: String?,
                            @JsonProperty("numero_cartao") var numeroCartao: String?,
                            @JsonProperty("data_validade") var dataValidade: String?,
                            @JsonProperty("codigo_seguranca") var codigo: String?,
                            @JsonProperty("forma_pagamento") var formaPagamento: String?)