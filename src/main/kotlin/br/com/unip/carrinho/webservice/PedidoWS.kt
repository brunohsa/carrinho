package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.ItemDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.dto.ProdutoCardapioDTO
import br.com.unip.carrinho.service.IPedidoService
import br.com.unip.carrinho.webservice.model.response.CarrinhoCriadoResponse
import br.com.unip.carrinho.webservice.model.response.ItemResponse
import br.com.unip.carrinho.webservice.model.response.PedidoResponse
import br.com.unip.carrinho.webservice.model.response.ProdutoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/pedidos"])
class PedidoWS(val pedidoService: IPedidoService) {

    @RequestMapping(value = ["/gerar"], method = [RequestMethod.POST])
    fun criarCardapio(): ResponseEntity<CarrinhoCriadoResponse> {
        val id = pedidoService.gerar()
        return ResponseEntity.ok(CarrinhoCriadoResponse(id))
    }

    @RequestMapping(value = ["/{pedido_id}/preparando"], method = [RequestMethod.PUT])
    fun preparando(@PathVariable("pedido_id") pedidoId: String): ResponseEntity<Void> {
        pedidoService.preparando(pedidoId)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(value = ["/{pedido_id}/concluido"], method = [RequestMethod.PUT])
    fun concluido(@PathVariable("pedido_id") pedidoId: String): ResponseEntity<Void> {
        pedidoService.concluido(pedidoId)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun buscar(): ResponseEntity<List<PedidoResponse>> {
        val id = pedidoService.buscarPedidos()
        return ResponseEntity.ok(map(id))
    }

    fun map(pedidos: List<PedidoDTO>): List<PedidoResponse> {
        return pedidos.map { p ->
            val itens = this.mapItens(p.itens!!)
            PedidoResponse(p.id, p.numero, itens)
        }
    }

    fun mapItens(itens: List<ItemDTO>): List<ItemResponse> {
        return itens.map { i ->
            val produto = map(i.produto!!)
            ItemResponse(produto, i.observacoes, i.quantidade, i.cliente)
        }
    }

    fun map(produto: ProdutoCardapioDTO): ProdutoResponse {
        return ProdutoResponse(produto.id, produto.nome)
    }
}