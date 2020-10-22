package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.carrinho.dto.AdicionarProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.CarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoCarrinhoDTO
import br.com.unip.carrinho.dto.ProdutoDTO
import br.com.unip.carrinho.exception.CarrinhoBaseException
import br.com.unip.carrinho.exception.CarrinhoPossuiProdutosDeOutroFornecedorException
import br.com.unip.carrinho.exception.ClienteJaPossuiCarrinhoAtivoException
import br.com.unip.carrinho.exception.ECodigoErro.CARRINHO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.ICarrinhoRepository
import br.com.unip.carrinho.repository.entity.Carrinho
import br.com.unip.carrinho.repository.entity.ProdutoCarrinho
import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho
import br.com.unip.carrinho.repository.entity.enums.EStatusCarrinho.FINALIZADO
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CarrinhoService(val carrinhoRepository: ICarrinhoRepository,
                      val produtoService: IProdutoService,
                      val cardapioService: ICardapioService) : ICarrinhoService {

    override fun criar(): CarrinhoDTO {
        val uuid = this.getCadastroUUID()
        val carrinhoPersistido = carrinhoRepository.buscarCarrinho(uuid)
        if (carrinhoPersistido.isPresent) {
            return map(carrinhoPersistido.get())
        }
        val carrinho = Carrinho(uuid, EStatusCarrinho.ATIVO)
        carrinhoRepository.save(carrinho)

        return map(carrinho)
    }

    override fun buscar(): CarrinhoDTO {
        return map(this.buscarCarrinho())
    }

    override fun buscarPorFornecedor(uuidFornecedor: String): List<CarrinhoDTO> {
        val carrinhos = carrinhoRepository.findByFornecedorUUIDAndStatus(uuidFornecedor)
        return carrinhos.map { c -> this.map(c) }
    }

    override fun finalizar(id: String) {
        val carrinho = carrinhoRepository.findById(id).get()
        carrinho.status = FINALIZADO

        carrinhoRepository.save(carrinho)
    }

    override fun adicionarProduto(dto: AdicionarProdutoCarrinhoDTO, cardapioId: String): CarrinhoDTO {
        val carrinho = this.buscarCarrinho()

        this.validarEAdicionarFornecedorUUID(carrinho, cardapioId)

        val produtos = carrinho.produtos.toMutableList()
        val produtoCarrinho = produtos.find { p -> p.produto.id == dto.id }
        if (produtoCarrinho != null) {
            this.atualizarQuantidadeProduto(produtoCarrinho, dto.quantidade)
        } else {
            this.adicionarNovoProduto(produtos, dto, cardapioId)
        }
        carrinho.produtos = produtos
        carrinhoRepository.save(carrinho)
        return this.map(carrinho)
    }

    private fun atualizarQuantidadeProduto(produtoCarrinho: ProdutoCarrinho, quantidade: Long) {
        produtoCarrinho.quantidade = produtoCarrinho.quantidade.plus(quantidade)
    }

    private fun adicionarNovoProduto(produtos: MutableList<ProdutoCarrinho>, dto: AdicionarProdutoCarrinhoDTO, cardapioId: String) {
        val produto = produtoService.buscarProduto(dto.id, cardapioId)
        val carrinhoProduto = ProdutoCarrinho(produto, dto.observacoes, dto.quantidade)
        produtos.add(carrinhoProduto)
    }

    private fun validarEAdicionarFornecedorUUID(carrinho: Carrinho, cardapioId: String) {
        val fornecedorUUID = cardapioService.buscarUUIDFornecedor(cardapioId)
        if (carrinho.fornecedorUUID == null) {
            carrinho.fornecedorUUID = fornecedorUUID
        } else if (carrinho.fornecedorUUID != fornecedorUUID) {
            throw CarrinhoPossuiProdutosDeOutroFornecedorException()
        }
    }

    override fun removerProduto(idProduto: String) {
        val carrinho = this.buscarCarrinho()
        val produtoCarrinho = carrinho.produtos.find { pc -> pc.produto.id == idProduto }
                ?: throw NaoEncontradoException(PRODUTO_NAO_ENCONTRADO)
        carrinho.removerProduto(produtoCarrinho)

        if (carrinho.produtos.isEmpty()) {
            carrinho.fornecedorUUID = null
        }
        carrinhoRepository.save(carrinho)
    }

    private fun buscarCarrinho(): Carrinho {
        val uuid = this.getCadastroUUID()
        return carrinhoRepository.buscarCarrinho(uuid)
                .orElseThrow { throw NaoEncontradoException(CARRINHO_NAO_ENCONTRADO) }
    }

    private fun map(carrinho: Carrinho): CarrinhoDTO {
        val produtos = this.map(carrinho.produtos)
        val valorTotal = this.somarValorTotal(produtos)

        return CarrinhoDTO(carrinho.id, produtos, valorTotal, carrinho.dataCriacao, carrinho.fornecedorUUID)
    }

    private fun map(produtosCarrinho: List<ProdutoCarrinho>): List<ProdutoCarrinhoDTO> {
        return produtosCarrinho.map { pc ->
            val produto = ProdutoDTO(pc.produto.id, pc.produto.nome, pc.produto.valor)
            ProdutoCarrinhoDTO(produto, pc.observacoes, pc.quantidade)
        }
    }

    private fun somarValorTotal(produtosCarrinho: List<ProdutoCarrinhoDTO>): BigDecimal {
        var valorTotal: BigDecimal = BigDecimal.ZERO
        produtosCarrinho.forEach { pc -> valorTotal += pc.produto.valor.multiply(BigDecimal(pc.quantidade)) }
        return valorTotal
    }

    private fun getCadastroUUID(): String {
        return AuthenticationUtil.getCadastroUUID()!!
    }
}