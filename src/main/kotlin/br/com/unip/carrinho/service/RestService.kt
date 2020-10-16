package br.com.unip.carrinho.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import kotlin.reflect.KClass


@Service
class RestService() : IRestService {

    private val restTemplate = RestTemplate()

    override fun <T : Any> get(uri: String, response: KClass<T>): T {
        return get(uri, response,  LinkedMultiValueMap())
    }

    override fun <T : Any> get(uri: String, response: KClass<T>, headers: LinkedMultiValueMap<String, String>): T {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.addAll(httpHeaders)

        val entity = HttpEntity(null, headers)
        return restTemplate.exchange(uri, GET, entity, response.java).body!!
    }
}