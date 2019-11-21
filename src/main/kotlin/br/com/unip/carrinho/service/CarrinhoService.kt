package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.CarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoDTO
import br.com.unip.carrinho.exception.ClienteJaPossuiCarrinhoAtivoException
import br.com.unip.carrinho.exception.ClienteNaoPossuiCarrinhoException
import br.com.unip.carrinho.repository.ICarrinhoRepository
import br.com.unip.carrinho.repository.entity.Carrinho
import br.com.unip.carrinho.repository.entity.ProdutoCarrinho
import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho
import br.com.unip.carrinho.security.util.AutenthicationUtil
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CarrinhoService(val carrinhoRepository: ICarrinhoRepository,
                      val produtoService: ICardapioService,
                      val autenticacaoService: IAutenticacaoService) : ICarrinhoService {

    override fun criar(): String {
        val email = AutenthicationUtil.getUsuarioLogado()
        val cadastro = autenticacaoService.buscarCadastroPorEmail(email)

        val possuiCarrinho = carrinhoRepository.buscarCarrinho(cadastro.uuid) != null
        if (possuiCarrinho) {
            throw ClienteJaPossuiCarrinhoAtivoException()
        }
        val uuidCliente = cadastro.uuid
        val carrinho = Carrinho(
                uuidCliente = uuidCliente,
                status = EStatusCarrinho.ATIVO
        )
        carrinhoRepository.save(carrinho)
        return carrinho.id!!
    }

    override fun buscar(): CarrinhoDTO {
        val email = AutenthicationUtil.getUsuarioLogado()
        val cadastro = autenticacaoService.buscarCadastroPorEmail(email)

        val carrinho = carrinhoRepository.buscarCarrinho(cadastro.uuid) ?: throw ClienteNaoPossuiCarrinhoException()
        return this.map(carrinho)
    }

    override fun finalizar(id: String) {
        val carrinho = carrinhoRepository.findById(id).orElseThrow { ClienteNaoPossuiCarrinhoException() }
        carrinho.status = EStatusCarrinho.FINALIZADO

        carrinhoRepository.save(carrinho)
    }

    override fun adicionarProduto(idCarrinho: String?, dto: AdicionarProdutoCarrinhoDTO): CarrinhoDTO {
        val carrinho = carrinhoRepository.findById(idCarrinho!!).orElseThrow { RuntimeException() }

        val produtos = carrinho.produtos!!.toMutableList()
        val produto = produtos.find { p -> p.produtoId == dto.id }
        if (produto != null) {
            produtos.remove(produto)
            produto.quantidade = produto.quantidade!!.plus(dto.quantidade!!)
            produtos.add(produto)
        } else {
            val produtoDto = produtoService.buscarProduto(dto.id!!)
            val carrinhoProduto = ProdutoCarrinho(produtoDto.id, dto.quantidade, dto.observacoes)
            produtos.add(carrinhoProduto)
        }
        carrinho.produtos = produtos
        carrinhoRepository.save(carrinho)
        return map(carrinho)
    }

    private fun map(carrinho: Carrinho): CarrinhoDTO {
        val produtosCarrinho = this.map(carrinho.produtos!!)
        val valorTotal = this.somarValorTotalCarrinho(produtosCarrinho)
        return CarrinhoDTO(carrinho.id, produtosCarrinho, valorTotal)
    }

    private fun somarValorTotalCarrinho(produtosCarrinho: List<ProdutoCarrinhoDTO>): BigDecimal {
        var valorTotal: BigDecimal = BigDecimal.ZERO
        produtosCarrinho.forEach { p ->
            val valor = p.produto!!.valor!!.multiply(BigDecimal(p.quantidade!!))
            valorTotal += valor
        }
        return valorTotal
    }

    private fun map(produtosCarrinho: List<ProdutoCarrinho>): List<ProdutoCarrinhoDTO> {
        return produtosCarrinho.map { pc ->
            val produtoCardapioDTO = produtoService.buscarProduto(pc.produtoId!!)
            val produtoDTO = ProdutoDTO(produtoCardapioDTO.id, produtoCardapioDTO.nome, BigDecimal(produtoCardapioDTO.valor))
            ProdutoCarrinhoDTO(produtoDTO, pc.quantidade, pc.observacoes)
        }.toList()
    }
}