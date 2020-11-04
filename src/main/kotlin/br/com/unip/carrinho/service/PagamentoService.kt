package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.repository.entity.Pagamento
import org.springframework.stereotype.Service

@Service
class PagamentoService : IPagamentoService {

    override fun pagar(dadosPagamento: DadosPagamentoDTO, valorPagamento: Double) : Pagamento {
        val pagamento = Pagamento(dadosPagamento.nomeCompleto!!, dadosPagamento.numeroCartao!!,
                dadosPagamento.dataValidade!!, dadosPagamento.formaPagamento!!, valorPagamento)

        return pagamento
    }
}