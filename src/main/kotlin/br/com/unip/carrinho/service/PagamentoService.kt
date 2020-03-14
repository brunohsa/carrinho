package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.repository.entity.Pagamento
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PagamentoService : IPagamentoService {

    override fun pagar(dadosPagamento: DadosPagamentoDTO, valorPagamento: BigDecimal) : Pagamento {
        val pagamento = Pagamento(dadosPagamento.nomeCompleto!!, dadosPagamento.numeroCartao!!,
                dadosPagamento.dataValidade!!, dadosPagamento.formaPagamento!!, valorPagamento)

        return pagamento
    }
}