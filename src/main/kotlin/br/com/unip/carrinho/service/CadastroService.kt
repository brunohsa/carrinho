package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.CadastroDTO
import br.com.unip.carrinho.dto.PessoaFisicaDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.util.LinkedMultiValueMap

@Repository
class CadastroService(val restService: IRestService) : ICadastroService {

    @Value("\${apikey.autenticacao}")
    private val APIKEY_AUTENTICACAO = ""

    @Value("\${microservice.cadastro.url}")
    private val CADASTRO_URL = ""

    private val CADASTRO_PESSOA_FISICA_URL = "v1/cadastros"

    private val APIKEY_TAG = "key"


    override fun buscarCadastro(cadastroUUID: String): CadastroDTO {
        val headers = getHeaderComApiKey()
        val url = "$CADASTRO_URL$CADASTRO_PESSOA_FISICA_URL/$cadastroUUID"
        return restService.get(url, CadastroDTO::class, headers)
    }

    private fun getHeaderComApiKey(): LinkedMultiValueMap<String, String> {
        val headers = LinkedMultiValueMap<String, String>()
        headers.add(APIKEY_TAG, APIKEY_AUTENTICACAO)

        return headers
    }
}