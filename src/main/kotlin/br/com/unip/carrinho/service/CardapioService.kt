package br.com.unip.carrinho.service

import br.com.unip.carrinho.exception.ECodigoErro.CARDAPIO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.ICardapioRepository
import br.com.unip.carrinho.repository.entity.Cardapio
import org.springframework.stereotype.Service


@Service
class CardapioService(val cardapioRepository: ICardapioRepository) : ICardapioService {

    override fun buscar(cardapioId: String): Cardapio {
        return  cardapioRepository.findById(cardapioId).orElseThrow { NaoEncontradoException(CARDAPIO_NAO_ENCONTRADO) }
    }

}