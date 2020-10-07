package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.DadosPagamentoDTO
import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.service.IPedidoService
import br.com.unip.carrinho.webservice.model.request.DadosPagamentoRequest
import br.com.unip.carrinho.webservice.model.response.ItemResponse
import br.com.unip.carrinho.webservice.model.response.PedidoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/pedidos"])
class PedidoWS(val pedidoService: IPedidoService) {

    @GetMapping
    fun buscar(@RequestParam("status") status: ArrayList<String>): ResponseEntity<List<PedidoResponse>> {
        val id = pedidoService.buscarPedidos(status)
        return ResponseEntity.ok(map(id))
    }

    @PostMapping(value = ["/gerar"])
    fun gerarPedido(): ResponseEntity<PedidoResponse> {
        val pedido = pedidoService.gerar()
        return ResponseEntity.ok(map(pedido))
    }

    @PostMapping(value = ["/{id_pedido}/pagar"])
    fun pagar(@PathVariable("id_pedido") pedidoId: String,
              @RequestBody pagamento: DadosPagamentoRequest): ResponseEntity<Void> {
        val dadosPagamentoDTO = DadosPagamentoDTO(pagamento.nomeCompleto, pagamento.numeroCartao,
                pagamento.dataValidade, pagamento.codigo, pagamento.formaPagamento, pagamento.salvar)

        pedidoService.pagar(pedidoId, dadosPagamentoDTO)
        return ResponseEntity.ok().build()
    }

    @PutMapping(value = ["/{id_pedido}/concluir"])
    fun concluir(@PathVariable("id_pedido") pedidoId: String): ResponseEntity<Void> {
        pedidoService.concluido(pedidoId)
        return ResponseEntity.ok().build()
    }

    fun map(pedidos: List<PedidoDTO>): List<PedidoResponse> {
        return pedidos.map { p -> map(p) }
    }

    fun map(pedido: PedidoDTO): PedidoResponse {
        return PedidoResponse(pedido.id, pedido.numero, mapItens(pedido.itens), pedido.valor)
    }

    fun mapItens(itens: List<ItemDTO>): List<ItemResponse> {
        return itens.map { i -> ItemResponse(i.produto, i.observacoes, i.quantidade) }
    }
}