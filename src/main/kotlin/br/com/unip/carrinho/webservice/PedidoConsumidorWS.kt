package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.service.IPedidoConsumidorService
import br.com.unip.carrinho.webservice.model.request.DadosPagamentoRequest
import br.com.unip.carrinho.webservice.model.response.ItemResponse
import br.com.unip.carrinho.webservice.model.response.PedidoResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/v1/pedidos/consumidores"])
class PedidoConsumidorWS(val pedidoService: IPedidoConsumidorService) {

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping
    fun buscar(@RequestParam("status") status: ArrayList<String>?,
               @RequestParam("de") de: String?,
               @RequestParam("para") para: String?,
               @RequestParam("limite") limite: Int?): ResponseEntity<List<PedidoResponse>> {
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
    @PostMapping(value = ["/avaliar"])
    fun avaliarPedido(): ResponseEntity<PedidoResponse> {
        val pedido = pedidoService.gerar()
        return ResponseEntity.ok(map(pedido))
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