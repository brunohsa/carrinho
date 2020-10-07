package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.repository.entity.Pagamento
import java.math.BigDecimal

interface IPagamentoService {

    fun pagar(dadosPagamento: DadosPagamentoDTO, valorPagamento: BigDecimal): Pagamento
}