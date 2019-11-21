package br.com.unip.carrinho.service

interface ISequenceService {

    fun getNextSequenceId(key: String): Long?
}