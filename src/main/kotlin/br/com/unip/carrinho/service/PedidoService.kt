package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.ClienteDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.exception.DataNaoPodeSerRetroativa
import br.com.unip.carrinho.exception.ECodigoErro.PEDIDO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.IPedidoRepository
import br.com.unip.carrinho.repository.entity.Cliente
import br.com.unip.carrinho.repository.entity.Item
import br.com.unip.carrinho.repository.entity.Pedido
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class PedidoService : IPedidoService {

    @Autowired
    private lateinit var pedidoRepository: IPedidoRepository

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    private val DATE_FORMAT: String = "dd/MM/yyyy"

    override fun buscarPedidos(filtro: FiltroPedidoDTO, uuidCadastro: String, keyWhere: String): List<PedidoDTO> {
        var criteria = Criteria.where(keyWhere).`is`(uuidCadastro)
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

    override fun buscarPedido(id: String, uuidCadastro: String): Pedido {
        val pedido = pedidoRepository.buscarPedido(id, uuidCadastro)
        if (pedido == null) {
            NaoEncontradoException(PEDIDO_NAO_ENCONTRADO)
        }
        return pedido!!
    }

    protected fun map(pedidos: List<Pedido>): List<PedidoDTO> {
        return pedidos.map { p -> p.toDTO() }
    }

    protected fun Pedido.toDTO() = PedidoDTO(this.id, this.numero, this.itens.toDTO(), this.status.toString(),
            this.valor, this.cliente.toDTO(), this.dataPedido)

    private fun List<Item>.toDTO() = this.map { i ->
         ItemDTO(i.id, i.produto, i.observacoes, i.quantidade, i.valor)
    }

    private fun Cliente.toDTO() = ClienteDTO(this.nome, this.telefone)
}