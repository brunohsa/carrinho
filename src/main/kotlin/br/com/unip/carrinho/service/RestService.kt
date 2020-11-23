package br.com.unip.carrinho.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.PUT
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import kotlin.reflect.KClass


@Service
class RestService(val mapper: ObjectMapper) : IRestService {
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

    override fun put(uri: String, request: Any, headers: LinkedMultiValueMap<String, String>) {
        val requestJson = mapper.writeValueAsString(request)

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.addAll(headers)

        val entity = HttpEntity(requestJson, httpHeaders)
        restTemplate.exchange(uri, PUT, entity, String::class.java).body!!
    }
}