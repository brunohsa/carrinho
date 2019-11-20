package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.ProdutoCardapioDTO
import org.springframework.stereotype.Service

@Service
class CardapioService(val restService: IRestService) : ICardapioService {

    override fun buscarProduto(idProduto: String): ProdutoCardapioDTO {
        return restService.get(" http://localhost:8083/cardapio/api/v1/produtos/$idProduto", ProdutoCardapioDTO::class)
    }
}