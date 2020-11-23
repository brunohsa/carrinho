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
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/fornecedores/pedidos"])
class PedidoFornecedorWS(val pedidoService: IPedidoFornecedorService) {

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping
    fun buscar(@ApiParam(required = false) @RequestParam("status") status: ArrayList<String>?,
               @ApiParam(required = false) @RequestParam("de") de: String?,
               @ApiParam(required = false) @RequestParam("ate") ate: String?,
               @ApiParam(required = false) @RequestParam("limite") limite: Int?): ResponseEntity<List<PedidoResponse>> {
        val filtro = FiltroPedidoDTO(status, de, ate, limite)
        val id = pedidoService.buscarPedidos(filtro)
        return ResponseEntity.ok(map(id))
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PutMapping(value = ["{id_pedido}/status/{status}/alterar"])
    fun alterarStatusPedido(@PathVariable(value = "id_pedido") idPedido: String,
                            @PathVariable(value = "status") status: String): ResponseEntity<Void> {
        pedidoService.alterarStatusPedido(idPedido, status)
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
        return itens.map { i -> ItemResponse(i.id, i.nome, i.observacoes, i.quantidade, i.valor, null) }
    }
}