package br.com.unip.carrinho.service

import br.com.unip.cardapio.security.util.AutenthicationUtil
import br.com.unip.carrinho.dto.CarrinhoDTO
import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoDTO
import br.com.unip.carrinho.repository.ICarrinhoRepository
import br.com.unip.carrinho.repository.entity.Carrinho
import br.com.unip.carrinho.repository.entity.Produto
import br.com.unip.carrinho.repository.entity.ProdutoCarrinho
import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CarrinhoService(val carrinhoRepository: ICarrinhoRepository,
                      val produtoService: ICardapioService,
                      val autenticacaoService: IAutenticacaoService) : ICarrinhoService {

    override fun criar(): String {
        val email = AutenthicationUtil.getUsuarioLogado()
        val cadastro = autenticacaoService.buscarCadastroPorEmail(email)

        val uuidCliente = cadastro.uuid
        val carrinho = Carrinho(
                uuidCliente = uuidCliente,
                status = EStatusCarrinho.PENDENTE
        )
        carrinhoRepository.save(carrinho)
        return carrinho.id!!
    }

    override fun adicionarProduto(idCarrinho: String?, dto: AdicionarProdutoCarrinhoDTO): CarrinhoDTO {
        val carrinho = carrinhoRepository.findById(idCarrinho!!).orElseThrow { RuntimeException() }

        val produto = carrinho.produtos!!.filter { p -> p.produto!!.id == dto.id }[0]
        if (produto != null) {
            produto.quantidade!!.plus(dto.quantidade!!)
        } else {
            val produtoDto = produtoService.buscarProduto(dto.id!!)
            val produto = Produto(produtoDto.id, produtoDto.nome, BigDecimal(produtoDto.valor))
            val carrinhoProduto = ProdutoCarrinho(produto, dto.quantidade)
            carrinho.produtos!!.plus(carrinhoProduto)
        }
        carrinhoRepository.save(carrinho)
        return map(carrinho)
    }

    private fun map(carrinho: Carrinho): CarrinhoDTO {
        val produtosCarringo = this.map(carrinho.produtos!!)
        val valorTotal = this.somarValorTotalCarrinho(produtosCarringo)
        return CarrinhoDTO(carrinho.id, produtosCarringo, valorTotal)
    }

    private fun somarValorTotalCarrinho(produtosCarrinho: List<ProdutoCarrinhoDTO>): BigDecimal {
        return produtosCarrinho.map { p -> p.produto!!.valor!! }.reduce { valor1, valor2 -> valor1 + valor2 }
    }

    private fun map(produtosCarrinho: List<ProdutoCarrinho>): List<ProdutoCarrinhoDTO> {
        return produtosCarrinho.map { pc ->
            val produto = pc.produto!!
            val produtoDTO = ProdutoDTO(produto.id, produto.nome, produto.valor)
            ProdutoCarrinhoDTO(produtoDTO, pc.quantidade)
        }.toList()
    }
}