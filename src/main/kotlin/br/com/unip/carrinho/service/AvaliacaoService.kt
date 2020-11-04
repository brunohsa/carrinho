package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
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
                       val produtoService: IProdutoService) : IAvaliacaoService {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun avaliarPedido(dto: AvaliarPedidoDTO) {
        if (dto.nota == null) {
            throw ParametroInvalidoException(ECodigoErro.CAMPO_NOTA_OBRIGATORIO)
        }
        val pedido = pedidoService.buscarPedido(dto.pedidoId, getCadatroUUID())
        validarSePodeAvaliar(pedido)

        val entity = dto.toEntity(pedido.fornecedorUUID)
        avaliacaoPedidoRepository.save(entity)
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

    private fun validarSePodeAvaliar(pedido: Pedido) {
        if (!pedido.status.isConcluido()) {
            throw ParametroInvalidoException(ECodigoErro.IMPOSSIVEL_AVALIAR_PEDIDO)
        }
    }

    private fun AvaliarPedidoDTO.toEntity(fornecedorUUID: String) = AvaliacaoPedido(getCadatroUUID(), fornecedorUUID, this.pedidoId, this.nota!!, this.comentario)

    private fun AvaliarProdutoDTO.toEntity() = AvaliacaoProduto(getCadatroUUID(), this.produtoId, this.nota!!)

    private fun getCadatroUUID(): String {
        return AuthenticationUtil.getCadastroUUID()!!
    }
}