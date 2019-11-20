package br.com.unip.carrinho.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import kotlin.reflect.KClass
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


@Service
class RestService(val mapper: ObjectMapper) : IRestService {

    private val restTemplate = RestTemplate()

    override fun <T : Any> post(uri: String, request: Any, response: KClass<T>): T {
        val res = post(uri, request)
        return mapper.readValue(res, response.java)
    }

    override fun post(uri: String, request: Any): String {
        val requestJson = mapper.writeValueAsString(request)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity(requestJson, headers)

        val res = restTemplate.postForEntity<String>(uri, entity, String::class)
        return res.body!!
    }

    override fun <T : Any> get(uri: String, response: KClass<T>): T {
        return restTemplate.getForEntity(uri, response.java).body!!
    }
}