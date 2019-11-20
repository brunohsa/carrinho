package br.com.unip.carrinho.service

import kotlin.reflect.KClass

interface IRestService {

    fun <T : Any> get(uri: String, response: KClass<T>): T

    fun <T : Any> post(uri: String, request: Any, response: KClass<T>): T

    fun post(uri: String, request: Any): String
}