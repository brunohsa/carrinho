package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.carrinho.dto.*
import br.com.unip.carrinho.repository.IPedidoRepository
import br.com.unip.carrinho.repository.entity.Cliente
import br.com.unip.carrinho.repository.entity.Item
import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.Sequence.PEDIDO_SEQUENCE
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido
import org.springframework.stereotype.Service


@Service
class PedidoConsumidorService(val carrinhoService: ICarrinhoService,
                              val pedidoRepository: IPedidoRepository,
                              val sequenceService: ISequenceService,
                              val pagamentoService: IPagamentoService,
                              val cadastroService: ICadastroService,
                              val avaliacaoService: IAvaliacaoService) : IPedidoConsumidorService, PedidoService() {

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
        val pedidos = this.buscarPedidos(filtro, getCadatroUUID(), "cadastroUUID")
        this.preencherAvaliacaoPedidos(pedidos)
        return pedidos
    }

    private fun preencherAvaliacaoPedidos(pedidos: List<PedidoDTO>) {
        pedidos.filter { p -> EStatusPedido.valueOf(p.status).isConcluido() }
               .forEach { p ->
                    val avaliacaoPedido = avaliacaoService.buscarAvaliacaoPedido(getCadatroUUID(), p.id)
                    p.avaliacao = avaliacaoPedido
                    this.preencherAvaliacaoItens(p, p.itens)
                }
    }

    private fun preencherAvaliacaoItens(pedido: PedidoDTO, itens: List<ItemDTO>) {
        itens.forEach { item ->
            val avaliacaoProduto = avaliacaoService.buscarAvaliacaoProduto(getCadatroUUID(), pedido.id, item.id)
            item.avaliacao = avaliacaoProduto
        }
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