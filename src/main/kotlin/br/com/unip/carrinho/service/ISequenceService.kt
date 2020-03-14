package br.com.unip.carrinho.service

interface ISequenceService {

    fun getSequenceNumeroPedido(key: String): String

    fun getSequence(key: String): Long
}