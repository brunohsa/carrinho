package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.carrinho.dto.AvaliacaoProdutoDTO
import br.com.unip.carrinho.dto.AvaliarPedidoDTO
import br.com.unip.carrinho.dto.AvaliarProdutoDTO
import br.com.unip.carrinho.dto.NotaMedia
import br.com.unip.carrinho.exception.ECodigoErro
import br.com.unip.carrinho.exception.ParametroInvalidoException
import br.com.unip.carrinho.repository.IAvaliacaoPedidoRepository
import br.com.unip.carrinho.repository.IAvaliacaoProdutoRepository
import br.com.unip.carrinho.repository.entity.AvaliacaoPedido
import br.com.unip.carrinho.repository.entity.AvaliacaoProduto
import br.com.unip.carrinho.repository.entity.Pedido
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class AvaliacaoService(val avaliacaoPedidoRepository: IAvaliacaoPedidoRepository,
                       val avaliacaoProdutoRepository: IAvaliacaoProdutoRepository,
                       val pedidoService: IPedidoService,
                       val produtoService: IProdutoService,
                       val cadastroService: ICadastroService) : IAvaliacaoService {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun buscarAvaliacaoPedido(clienteUUID: String, pedidoId: String): AvaliarPedidoDTO? {
        return avaliacaoPedidoRepository.findByusuarioUUIDAndPedidoId(clienteUUID, pedidoId)?.toDTO()
    }

    override fun buscarAvaliacaoProduto(clienteUUID: String, pedidoId: String, produtoId: String): AvaliacaoProdutoDTO? {
        return avaliacaoProdutoRepository.findByUsuarioUUIDAndPedidoIdAndProdutoId(clienteUUID, pedidoId, produtoId)?.toDTO()
    }

    override fun buscarAvaliacaoProdutos(clienteUUID: String, pedidoId: String): List<AvaliacaoProdutoDTO> {
        return avaliacaoProdutoRepository.findByUsuarioUUIDAndPedidoId(clienteUUID, pedidoId).map { a -> a.toDTO() }
    }

    override fun avaliarPedido(dto: AvaliarPedidoDTO) {
        if (dto.nota == null) {
            throw ParametroInvalidoException(ECodigoErro.CAMPO_NOTA_OBRIGATORIO)
        }
        val pedido = pedidoService.buscarPedido(dto.pedidoId, getCadatroUUID())
        validarSePodeAvaliar(pedido)

        val entity = dto.toEntity(pedido.fornecedorUUID)
        avaliacaoPedidoRepository.save(entity)

        this.atualizarNotaMediaFornecedor(pedido.fornecedorUUID)
    }

    private fun atualizarNotaMediaFornecedor(fornecedorUUID: String) {
        CompletableFuture.runAsync {
            val nota = buscarNotaMediaFornecedor(fornecedorUUID)
            if (nota != null) {
                cadastroService.atualizarNotaMediaFornecedor(fornecedorUUID, nota.media)
            }
        }
    }

    override fun avaliarProduto(dto: AvaliarProdutoDTO) {
        if (dto.nota == null) {
            throw ParametroInvalidoException(ECodigoErro.CAMPO_NOTA_OBRIGATORIO)
        }
        val pedido = pedidoService.buscarPedido(dto.pedidoId, getCadatroUUID())
        validarSePodeAvaliar(pedido)

        val entity = dto.toEntity()
        avaliacaoProdutoRepository.save(entity)

        this.atualizarNotaMediaProduto(dto.produtoId)
    }

    private fun atualizarNotaMediaProduto(idProduto: String) {
        CompletableFuture.runAsync {
            val nota = buscarNotaMediaProduto(idProduto)
            if (nota != null) {
                produtoService.atualizarNotaMedia(idProduto, nota.media)
            }
        }
    }

    private fun buscarNotaMediaProduto(produtoId: String): NotaMedia? {
        val aggregation = newAggregation(
                AvaliacaoProduto::class.java,
                match(Criteria.where("produtoId").`is`(produtoId)),
                group("produtoId").avg("nota").`as`("media")
        )
        return mongoTemplate.aggregate(aggregation, NotaMedia::class.java).uniqueMappedResult
    }

    private fun buscarNotaMediaFornecedor(fornecedorUUID: String): NotaMedia? {
        val aggregation = newAggregation(
                AvaliacaoPedido::class.java,
                match(Criteria.where("fornecedorUUID").`is`(fornecedorUUID)),
                group("fornecedorUUID").avg("nota").`as`("media")
        )
        return mongoTemplate.aggregate(aggregation, NotaMedia::class.java).uniqueMappedResult
    }

    private fun validarSePodeAvaliar(pedido: Pedido) {
        if (!pedido.status.isConcluido()) {
            throw ParametroInvalidoException(ECodigoErro.IMPOSSIVEL_AVALIAR_PEDIDO)
        }
    }

    private fun AvaliarPedidoDTO.toEntity(fornecedorUUID: String) = AvaliacaoPedido(getCadatroUUID(), fornecedorUUID, this.pedidoId, this.nota!!, this.comentario)

    private fun AvaliarProdutoDTO.toEntity() = AvaliacaoProduto(getCadatroUUID(), this.pedidoId, this.produtoId, this.nota!!)

    private fun AvaliacaoPedido.toDTO() = AvaliarPedidoDTO(this.pedidoId, this.nota, this.comentario)

    private fun AvaliacaoProduto.toDTO() = AvaliacaoProdutoDTO(this.id, this.usuarioUUID, this.pedidoId, this.produtoId, this.nota)

    private fun getCadatroUUID(): String {
        return AuthenticationUtil.getCadastroUUID()!!
    }
}