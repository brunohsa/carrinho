package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.ClienteDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.service.IPedidoFornecedorService
import br.com.unip.carrinho.webservice.model.response.ClienteResponse
import br.com.unip.carrinho.webservice.model.response.ItemResponse
import br.com.unip.carrinho.webservice.model.response.PedidoResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/v1/fornecedores/pedidos"])
class PedidoFornecedorWS(val pedidoService: IPedidoFornecedorService) {

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping
    fun buscar(@RequestParam("status") status: ArrayList<String>?,
               @RequestParam("de") de: String?,
               @RequestParam("ate") ate: String?,
               @RequestParam("limite") limite: Int?): ResponseEntity<List<PedidoResponse>> {
        val filtro = FiltroPedidoDTO(status, de, ate, limite)
        val id = pedidoService.buscarPedidos(filtro)
        return ResponseEntity.ok(map(id))
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PutMapping(value = ["/{id_pedido}/concluir"])
    fun concluir(@PathVariable("id_pedido") pedidoId: String): ResponseEntity<Void> {
        pedidoService.concluido(pedidoId)
        return ResponseEntity.ok().build()
    }

    private fun map(pedidos: List<PedidoDTO>): List<PedidoResponse> {
        return pedidos.map { p -> map(p) }
    }

    private fun map(pedido: PedidoDTO): PedidoResponse {
        return PedidoResponse(pedido.id, pedido.numero, pedido.status, mapItens(pedido.itens), pedido.valor,
                pedido.cliente.toResponse(), pedido.dataPedido)
    }

    private fun ClienteDTO.toResponse() = ClienteResponse(this.nome, this.telefone)

    private fun mapItens(itens: List<ItemDTO>): List<ItemResponse> {
        return itens.map { i -> ItemResponse(i.id, i.nome, i.observacoes, i.quantidade, i.valor) }
    }
}