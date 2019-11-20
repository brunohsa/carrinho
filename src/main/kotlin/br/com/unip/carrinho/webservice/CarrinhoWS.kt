package br.com.unip.carrinho.webservice

import br.com.unip.carrinho.dto.CarrinhoDTO
import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.service.ICarrinhoService
import br.com.unip.carrinho.webservice.model.request.ProdutoRequest
import br.com.unip.carrinho.webservice.model.response.CarrinhoResponse
import br.com.unip.carrinho.webservice.model.response.ItemCarrinhoResponse
import br.com.unip.carrinho.webservice.model.response.ProdutoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/carrinho"])
class CarrinhoWS(val carrinhoService: ICarrinhoService) {

    @RequestMapping(value = ["/criar"], method = [RequestMethod.POST])
    fun criarCardapio(): ResponseEntity<String> {
        val id = carrinhoService.criar()
        return ResponseEntity.ok(id)
    }

    @RequestMapping(value = ["/{id_carrinho}/adicionar-produto"], method = [RequestMethod.PUT])
    fun adicionarProduto(@RequestParam("id_carrinho") idCarrinho: String?,
                         @RequestBody request: ProdutoRequest): ResponseEntity<CarrinhoResponse> {
        val produto = AdicionarProdutoCarrinhoDTO(request.id, request.quantidade)
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
            val produto = ProdutoResponse(produtoDTO.id, produtoDTO.nome, produtoDTO.toString())
            ItemCarrinhoResponse(produto, p.quantidade)
        }.toList()
    }
}