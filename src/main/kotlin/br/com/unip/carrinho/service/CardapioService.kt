package br.com.unip.carrinho.service

import br.com.unip.carrinho.exception.ECodigoErro.CARDAPIO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.ICardapioRepository
import org.springframework.stereotype.Service


@Service
class CardapioService(val cardapioRepository: ICardapioRepository) : ICardapioService {

    override fun buscarUUIDFornecedor(cardapioId: String): String {
        val cardapio = cardapioRepository.findById(cardapioId).orElseThrow { NaoEncontradoException(CARDAPIO_NAO_ENCONTRADO) }
        return cardapio.uuidFornecedor!!
    }

}