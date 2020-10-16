package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.CarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.service.ICarrinhoService
import br.com.unip.carrinho.service.IProdutoService
import br.com.unip.carrinho.webservice.model.request.ProdutoRequest
import br.com.unip.carrinho.webservice.model.response.CarrinhoCriadoResponse
import br.com.unip.carrinho.webservice.model.response.CarrinhoResponse
import br.com.unip.carrinho.webservice.model.response.ItemCarrinhoResponse
import br.com.unip.carrinho.webservice.model.response.ProdutoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/carrinhos"])
class CarrinhoWS(val carrinhoService: ICarrinhoService, val prod: IProdutoService) {

    @PostMapping(value = ["/criar"])
    fun criarCardapio(): ResponseEntity<CarrinhoCriadoResponse> {
        val id = carrinhoService.criar()
        return ResponseEntity.ok(CarrinhoCriadoResponse(id))
    }

    @GetMapping(value = [""])
    fun buscar(): ResponseEntity<CarrinhoResponse> {
        val carrinho = carrinhoService.buscar()
        return ResponseEntity.ok(this.map(carrinho))
    }

    @PutMapping(value = ["/cardapio/{id_cardapio}/produto/{id_produto}/adicionar"])
    fun adicionarProduto(@PathVariable(value = "id_cardapio") idCardapio: String,
                         @PathVariable(value = "id_produto") idProduto: String,
                         @RequestBody request: ProdutoRequest) : ResponseEntity<CarrinhoResponse> {
        val produto = AdicionarProdutoCarrinhoDTO(idProduto, request.observacoes, request.quantidade)
        val dto = carrinhoService.adicionarProduto(produto, idCardapio)

        return ResponseEntity.ok(this.map(dto))
    }

    @DeleteMapping(value = ["/produto/{id_produto}"])
    fun removerProduto(@PathVariable(value = "id_produto") idProduto: String): ResponseEntity<Void> {
        carrinhoService.removerProduto(idProduto)
        return ResponseEntity.ok().build()
    }

    private fun map(carrinhoDTO: CarrinhoDTO): CarrinhoResponse {
        var itensResponse = this.map(carrinhoDTO.produtos)
        return CarrinhoResponse(carrinhoDTO.id, itensResponse, carrinhoDTO.valorTotal, carrinhoDTO.dataCriacao)
    }

    private fun map(produtosCarrinhoDTO: List<ProdutoCarrinhoDTO>): List<ItemCarrinhoResponse> {
        return produtosCarrinhoDTO.map { p ->
            val produto = ProdutoResponse(p.produto.id, p.produto.nome, p.produto.valor.toString())
            ItemCarrinhoResponse(produto, p.quantidade, p.observacoes)
        }
    }
}