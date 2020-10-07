package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.exception.ECodigoErro.PEDIDO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.IPedidoRepository
import br.com.unip.carrinho.repository.entity.Item
import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.Sequence.PEDIDO_SEQUENCE
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
class PedidoService(val carrinhoService: ICarrinhoService,
                    val pedidoRepository: IPedidoRepository,
                    val sequenceService: ISequenceService,
                    val mongoTemplate: MongoTemplate,
                    val pagamentoService: IPagamentoService) : IPedidoService {

    override fun gerar(): PedidoDTO {
        val cadastroUUID = getCadatroUUID()

        val carrinho = carrinhoService.buscar()
        val itens = carrinho.produtos.map { pc -> Item(pc.produto.nome, pc.observacoes, pc.quantidade) }
        val numero = sequenceService.getSequenceNumeroPedido("$PEDIDO_SEQUENCE-${cadastroUUID}")
        val valorTotal = calcularValorPedido(carrinho.produtos)

        val pedido = Pedido(cadastroUUID, numero, itens, valorTotal)
        pedidoRepository.save(pedido)
        carrinhoService.finalizar(carrinho.id)

        return map(pedido)
    }

    private fun calcularValorPedido(produtosCarrinho: List<ProdutoCarrinhoDTO>): BigDecimal {
        var valorTotal: BigDecimal = BigDecimal.ZERO
        produtosCarrinho.forEach { pc -> valorTotal += pc.produto.valor.multiply(BigDecimal(pc.quantidade)) }
        return valorTotal
    }

    override fun buscarPedidos(status: List<String>): List<PedidoDTO> {
        var criteria = Criteria.where("cadastroUUID").`is`(getCadatroUUID())
        if (status.isNotEmpty()) {
            criteria = criteria.and("status").`in`(status)
        }
        val query = Query().addCriteria(criteria)
        val pedidos = mongoTemplate.find(query, Pedido::class.java)

        return this.map(pedidos)
    }

    override fun concluido(pedidoId: String) {
        val pedido = buscarPedido(pedidoId)
        pedido.paraConcluido()

        pedidoRepository.save(pedido)
    }

    override fun pagar(id: String, dadosPagamento: DadosPagamentoDTO) {
        val pedido = buscarPedido(id)
        val pagamento = pagamentoService.pagar(dadosPagamento, pedido.valor)
        pedido.pagamento = pagamento
        pedido.paraPendentePreparacao()

        pedidoRepository.save(pedido)
    }

    private fun buscarPedido(id: String): Pedido {
        return pedidoRepository.findById(id).orElseThrow { NaoEncontradoException(PEDIDO_NAO_ENCONTRADO) }
    }

    private fun map(pedidos: List<Pedido>): List<PedidoDTO> {
        return pedidos.map { p -> map(p) }
    }

    private fun map(pedido: Pedido): PedidoDTO {
        return PedidoDTO(pedido.id, pedido.numero, mapItens(pedido.itens), pedido.status.toString(), pedido.valor)
    }

    private fun mapItens(itens: List<Item>): List<ItemDTO> {
        return itens.map { i -> ItemDTO(i.produto, i.observacoes, i.quantidade) }
    }

    private fun getCadatroUUID(): String {
        return AuthenticationUtil.getCadastroUUID()!!
    }
}