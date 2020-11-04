package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.*
import br.com.unip.carrinho.service.IAvaliacaoService
import br.com.unip.carrinho.service.IPedidoConsumidorService
import br.com.unip.carrinho.service.IPedidoService
import br.com.unip.carrinho.service.PedidoService
import br.com.unip.carrinho.webservice.model.request.AvaliarPedidoRequest
import br.com.unip.carrinho.webservice.model.request.AvaliarProdutoRequest
import br.com.unip.carrinho.webservice.model.request.DadosPagamentoRequest
import br.com.unip.carrinho.webservice.model.response.ItemResponse
import br.com.unip.carrinho.webservice.model.response.PedidoResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/v1/consumidores/pedidos"])
class PedidoConsumidorWS(val pedidoService: IPedidoConsumidorService,
                         val avaliacaoService: IAvaliacaoService) {

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping
    fun buscar(@ApiParam(required = false) @RequestParam("status") status: ArrayList<String>?,
               @ApiParam(required = false) @RequestParam("de") de: String?,
               @ApiParam(required = false) @RequestParam("ate") para: String?,
               @ApiParam(required = false) @RequestParam("limite") limite: Int?): ResponseEntity<List<PedidoResponse>> {
        val filtro = FiltroPedidoDTO(status, de, para, limite)
        val id = pedidoService.buscarPedidos(filtro)
        return ResponseEntity.ok(map(id))
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PostMapping(value = ["/gerar"])
    fun gerarPedido(@RequestBody pagamento: DadosPagamentoRequest): ResponseEntity<PedidoResponse> {
        val pedido = pedidoService.gerar()
        val pedidoPago = pedidoService.pagar(pedido.id, pagamento.toDTO())
        return ResponseEntity.ok(map(pedidoPago))
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PostMapping(value = ["{id_pedido}/avaliar"])
    fun avaliarPedido(@PathVariable(value = "id_pedido") idPedido: String,
                      @RequestBody avaliacao: AvaliarPedidoRequest): ResponseEntity<Void> {
        val dto = AvaliarPedidoDTO(idPedido, avaliacao.nota, avaliacao.comentario)
        avaliacaoService.avaliarPedido(dto)

        return ResponseEntity.ok().build()
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PostMapping(value = ["{id_pedido}/produto/{id_produto}/avaliar"])
    fun avaliarProduto(@PathVariable(value = "id_pedido") idPedido: String,
                       @PathVariable(value = "id_produto") idProduto: String,
                       @RequestBody avaliacao: AvaliarProdutoRequest): ResponseEntity<Void> {
        val dto = AvaliarProdutoDTO(idPedido, idProduto, avaliacao.nota)
        avaliacaoService.avaliarProduto(dto)

        return ResponseEntity.ok().build()
    }

    private fun DadosPagamentoRequest.toDTO() = DadosPagamentoDTO(this.nomeCompleto, this.numeroCartao, this.dataValidade, this.codigo, this.formaPagamento)

    private fun map(pedidos: List<PedidoDTO>): List<PedidoResponse> {
        return pedidos.map { p -> map(p) }
    }

    private fun map(pedido: PedidoDTO): PedidoResponse {
        return PedidoResponse(pedido.id, pedido.numero, pedido.status, mapItens(pedido.itens), pedido.valor, pedido.dataPedido)
    }

    private fun mapItens(itens: List<ItemDTO>): List<ItemResponse> {
        return itens.map { i -> ItemResponse(i.id, i.nome, i.observacoes, i.quantidade, i.valor) }
    }
}