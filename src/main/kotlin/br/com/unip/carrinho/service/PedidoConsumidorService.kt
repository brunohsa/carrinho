package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.repository.IPedidoRepository
import br.com.unip.carrinho.repository.entity.Cliente
import br.com.unip.carrinho.repository.entity.Item
import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.Sequence.PEDIDO_SEQUENCE
import org.springframework.stereotype.Service


@Service
class PedidoConsumidorService(val carrinhoService: ICarrinhoService,
                              val pedidoRepository: IPedidoRepository,
                              val sequenceService: ISequenceService,
                              val pagamentoService: IPagamentoService,
                              val cadastroService: ICadastroService) : IPedidoConsumidorService, PedidoService() {

    override fun gerar(): PedidoDTO {
        val cadastroUUID = getCadatroUUID()

        val carrinho = carrinhoService.buscar()
        val itens = carrinho.produtos.toEntity()
        val numero = sequenceService.getSequenceNumeroPedido("$PEDIDO_SEQUENCE")
        val valorTotal = this.calcularValorPedido(carrinho.produtos)
        val cliente = getCliente(cadastroUUID)

        val pedido = Pedido(carrinho.fornecedorUUID!!, cadastroUUID, numero, itens, valorTotal, cliente)
        pedidoRepository.save(pedido)
        carrinhoService.finalizar(carrinho.id)

        return pedido.toDTO()
    }

    override fun buscarPedido(id: String): Pedido {
        return pedidoRepository.findById(id).get()
    }

    override fun buscarPedidos(filtro: FiltroPedidoDTO): List<PedidoDTO> {
        return this.buscarPedidos(filtro, getCadatroUUID(), "cadastroUUID")
    }

    override fun pagar(id: String, dadosPagamento: DadosPagamentoDTO): PedidoDTO {
        val pedido = buscarPedido(id, getCadatroUUID())
        val pagamento = pagamentoService.pagar(dadosPagamento, pedido.valor)
        pedido.pagamento = pagamento
        pedido.paraPendentePreparacao()

        pedidoRepository.save(pedido)
        return pedido.toDTO()
    }

    private fun calcularValorPedido(produtosCarrinho: List<ProdutoCarrinhoDTO>): Double {
        var valorTotal = 0.0
        produtosCarrinho.forEach { pc -> valorTotal += pc.produto.valor * pc.quantidade }
        return valorTotal
    }

    private fun List<ProdutoCarrinhoDTO>.toEntity() = this.map { pc ->
        Item(pc.produto.id, pc.produto.nome, pc.observacoes, pc.quantidade, pc.produto.valor)
    }

    private fun getCliente(cadastroUUID: String): Cliente {
        val cadastroDTO = cadastroService.buscarCadastro(cadastroUUID)
        val pessoa = cadastroDTO.pessoa
        var nomeCliente = pessoa.nome!!
        if (pessoa.sobrenome != null) {
            nomeCliente += " ${pessoa.sobrenome}"
        }
        return Cliente(nomeCliente, pessoa.telefone)
    }

    private fun getCadatroUUID(): String {
        return AuthenticationUtil.getCadastroUUID()!!
    }
}