package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.carrinho.dto.ClienteDTO
import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.exception.DataNaoPodeSerRetroativa
import br.com.unip.carrinho.exception.ECodigoErro.PEDIDO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.IPedidoRepository
import br.com.unip.carrinho.repository.entity.Cliente
import br.com.unip.carrinho.repository.entity.Item
import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.Sequence.PEDIDO_SEQUENCE
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class PedidoService(val carrinhoService: ICarrinhoService,
                    val pedidoRepository: IPedidoRepository,
                    val sequenceService: ISequenceService,
                    val mongoTemplate: MongoTemplate,
                    val pagamentoService: IPagamentoService,
                    val cadastroService: ICadastroService) : IPedidoService {

    private val DATE_FORMAT: String = "dd/MM/yyyy"

    override fun gerar(): PedidoDTO {
        val cadastroUUID = getCadatroUUID()

        val carrinho = carrinhoService.buscar()
        val itens = mapPC(carrinho.produtos)
        val numero = sequenceService.getSequenceNumeroPedido("$PEDIDO_SEQUENCE-${carrinho.fornecedorUUID}")
        val valorTotal = this.calcularValorPedido(carrinho.produtos)
        val cliente = getCliente(cadastroUUID)

        val pedido = Pedido(carrinho.fornecedorUUID!!, cadastroUUID, numero, itens, valorTotal, cliente)
        pedidoRepository.save(pedido)
        carrinhoService.finalizar(carrinho.id)

        return pedido.toDTO()
    }

    private fun calcularValorPedido(produtosCarrinho: List<ProdutoCarrinhoDTO>): BigDecimal {
        var valorTotal: BigDecimal = BigDecimal.ZERO
        produtosCarrinho.forEach { pc -> valorTotal += pc.produto.valor.multiply(BigDecimal(pc.quantidade)) }
        return valorTotal
    }

    override fun buscarPedidosConsumidor(filtro: FiltroPedidoDTO): List<PedidoDTO> {
        return this.buscarPedidos(filtro, "cadastroUUID")
    }

    override fun buscarPedidosFornecedor(filtro: FiltroPedidoDTO): List<PedidoDTO> {
        return this.buscarPedidos(filtro, "fornecedorUUID")
    }

    private fun buscarPedidos(filtro: FiltroPedidoDTO, keyWhere: String): List<PedidoDTO> {
        var criteria = Criteria.where(keyWhere).`is`(getCadatroUUID())
        if (filtro.status != null && filtro.status.isNotEmpty()) {
            criteria = criteria.and("status").`in`(filtro.status)
        }
        if (filtro.de != null && filtro.ate != null) {
            val dataDe = filtro.de.toLocalDate(0, 0)
            val dataAte = filtro.ate.toLocalDate(23, 59)

            this.validarRetroatividade(dataDe, dataAte)

            criteria = criteria.and("dataPedido").gte(dataDe).lt(dataAte)
        }

        val query = Query().addCriteria(criteria)
        query.with(Sort(Direction.DESC, "dataPedido"))

        if (filtro.limite != null && filtro.limite > 0) {
            query.limit(filtro.limite)
        }
        return this.map(mongoTemplate.find(query, Pedido::class.java))
    }

    private fun validarRetroatividade(de: LocalDateTime, ate: LocalDateTime) {
        if (de.isAfter(ate)) {
            throw DataNaoPodeSerRetroativa()
        }
    }

    private fun String.toLocalDate(hora: Int, minutos: Int): LocalDateTime {
        val format = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return LocalDate.parse(this, format)!!.atTime(hora, minutos)
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

    private fun getCliente(cadastroUUID: String): Cliente {
        val clienteDTO = cadastroService.buscarPessoaFisica(cadastroUUID)
        val nomeCliente = "${clienteDTO.nome} ${clienteDTO.sobrenome}"
        return Cliente(nomeCliente, clienteDTO.telefone)
    }

    private fun mapPC(produtoCarrinho: List<ProdutoCarrinhoDTO>): List<Item> {
        return produtoCarrinho.map { pc ->
            val produto = pc.produto
            Item(produto.id, pc.produto.nome, pc.observacoes, pc.quantidade, produto.valor)
        }.toList()
    }

    private fun map(pedidos: List<Pedido>): List<PedidoDTO> {
        return pedidos.map { p -> p.toDTO() }
    }

    private fun Pedido.toDTO() =
            PedidoDTO(this.id, this.numero, this.itens.toDTO(), this.status.toString(), this.valor, this.cliente.toDTO(), this.dataPedido)

    private fun List<Item>.toDTO() = this.map { i -> ItemDTO(i.id, i.produto, i.observacoes, i.quantidade, i.valor) }

    private fun Cliente.toDTO() = ClienteDTO(this.nome, this.telefone)

    private fun getCadatroUUID(): String {
        return AuthenticationUtil.getCadastroUUID()!!
    }
}