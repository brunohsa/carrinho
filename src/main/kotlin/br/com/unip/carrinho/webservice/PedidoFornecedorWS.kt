package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.ClienteDTO
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.service.IPedidoService
import br.com.unip.carrinho.webservice.model.response.ClienteResponse
import br.com.unip.carrinho.webservice.model.response.ItemResponse
import br.com.unip.carrinho.webservice.model.response.PedidoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping(value = ["/v1/pedidos/fornecedores"])
class PedidoFornecedorWS(val pedidoService: IPedidoService) {

    @GetMapping
    fun buscar(@RequestParam("status") status: ArrayList<String>?,
               @RequestParam("de") de: String?,
               @RequestParam("ate") ate: String?,
               @RequestParam("limite") limite: Int?): ResponseEntity<List<PedidoResponse>> {
        val filtro = FiltroPedidoDTO(status, de, ate, limite)
        val id = pedidoService.buscarPedidosFornecedor(filtro)
        return ResponseEntity.ok(map(id))
    }

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