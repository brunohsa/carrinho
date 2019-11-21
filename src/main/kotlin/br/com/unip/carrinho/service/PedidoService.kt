package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.repository.IPedidoRepository
import br.com.unip.carrinho.repository.entity.Item
import br.com.unip.carrinho.repository.entity.Pedido
import br.com.unip.carrinho.repository.entity.enums.EStatusPedido
import br.com.unip.carrinho.security.util.AutenthicationUtil
import br.com.unip.carrinho.webservice.model.response.ItemResponse
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.stereotype.Service
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.lang.RuntimeException


@Service
class PedidoService(val carrinhoService: ICarrinhoService,
                    val autenticacaoService: IAutenticacaoService,
                    val pedidoRepository: IPedidoRepository,
                    val sequenceService: ISequenceService,
                    val mongoTemplate: MongoTemplate,
                    val produtoService: ICardapioService) : IPedidoService {

    override fun gerar(): String {
        val email = AutenthicationUtil.getUsuarioLogado()
        val cadastro = autenticacaoService.buscarCadastroPorEmail(email)

        val carrinho = carrinhoService.buscar()
        val produtosCarrinho = carrinho.produtos!!
        val itens = produtosCarrinho.map { pc ->
            val produto = pc.produto!!
            Item(produto.id, pc.observacoes, pc.quantidade, cadastro.pessoa.nome)
        }
        val numero = sequenceService.getNextSequenceId("pedido_sequence")
        val pedido = Pedido(cadastro.uuid, numero, itens)

        pedidoRepository.save(pedido)
        carrinhoService.finalizar(carrinho.id!!)

        return pedido.id!!
    }

    override fun concluido(pedidoId: String) {
        this.mudarStatus(pedidoId, EStatusPedido.CONCLUIDO)
    }

    override fun preparando(pedidoId: String) {
        this.mudarStatus(pedidoId, EStatusPedido.PREPARANDO)
    }

    private fun mudarStatus(pedidoId: String, status: EStatusPedido) {
        val pedido = pedidoRepository.findById(pedidoId).orElseThrow { RuntimeException("Pedido não encontrado") }
        pedido.status = status

        pedidoRepository.save(pedido)
    }

    override fun buscarPedidos(): List<PedidoDTO> {
        val email = AutenthicationUtil.getUsuarioLogado()
        val cadastro = autenticacaoService.buscarCadastroPorEmail(email)

        val query = Query()
        query.addCriteria(Criteria.where("uuidCliente").`is`(cadastro.uuid)
                .and("status").ne(EStatusPedido.CONCLUIDO))

        val pedidos = mongoTemplate.find(query, Pedido::class.java)
        return this.map(pedidos)
    }

    fun map(pedidos: List<Pedido>): List<PedidoDTO> {
        return pedidos.map { p ->
            val itens = this.mapItens(p.itens!!)
            PedidoDTO(p.id, p.uuidCliente, p.numero, itens, p.status.toString())
        }
    }

    fun mapItens(itens: List<Item>): List<ItemDTO> {
        return itens.map { i ->
            val produto = produtoService.buscarProduto(i.produtoId!!)
            ItemDTO(produto, i.observacoes, i.quantidade, i.cliente)
        }
    }
}