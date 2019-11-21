package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.CarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.service.ICarrinhoService
import br.com.unip.carrinho.webservice.model.request.ProdutoRequest
import br.com.unip.carrinho.webservice.model.response.CarrinhoCriadoResponse
import br.com.unip.carrinho.webservice.model.response.CarrinhoResponse
import br.com.unip.carrinho.webservice.model.response.ItemCarrinhoResponse
import br.com.unip.carrinho.webservice.model.response.ProdutoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/v1/carrinhos"])
class CarrinhoWS(val carrinhoService: ICarrinhoService) {

    @RequestMapping(value = ["/criar"], method = [RequestMethod.POST])
    fun criarCardapio(): ResponseEntity<CarrinhoCriadoResponse> {
        val id = carrinhoService.criar()
        return ResponseEntity.ok(CarrinhoCriadoResponse(id))
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun buscar(): ResponseEntity<CarrinhoResponse> {
        val carrinho = carrinhoService.buscar()
        return ResponseEntity.ok(this.map(carrinho))
    }

    @RequestMapping(value = ["/{id_carrinho}/adicionar-produto"], method = [RequestMethod.PUT])
    fun adicionarProduto(@PathVariable("id_carrinho") idCarrinho: String?,
                         @RequestBody request: ProdutoRequest): ResponseEntity<CarrinhoResponse> {
        val produto = AdicionarProdutoCarrinhoDTO(request.id, request.quantidade, request.observacoes)
        val dto = carrinhoService.adicionarProduto(idCarrinho, produto)

        val response = this.map(dto)
        return ResponseEntity.ok(response)
    }

    private fun map(carrinhoDTO: CarrinhoDTO): CarrinhoResponse {
        var itensResponse = this.map(carrinhoDTO.produtos!!)
        return CarrinhoResponse(carrinhoDTO.id, itensResponse, carrinhoDTO.valorTotal)
    }

    private fun map(produtosCarrinhoDTO: List<ProdutoCarrinhoDTO>): List<ItemCarrinhoResponse> {
        return produtosCarrinhoDTO.map { p ->
            val produtoDTO = p.produto!!
            val produto = ProdutoResponse(produtoDTO.id, produtoDTO.nome, produtoDTO.valor.toString())
            ItemCarrinhoResponse(produto, p.quantidade, p.observacoes)
        }.toList()
    }
}