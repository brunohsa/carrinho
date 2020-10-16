package br.com.unip.carrinho.service

import org.springframework.util.LinkedMultiValueMap
import kotlin.reflect.KClass

interface IRestService {

    fun <T : Any> get(uri: String, response: KClass<T>): T

    fun <T : Any> get(uri: String, response: KClass<T>, headers: LinkedMultiValueMap<String, String>): T
}