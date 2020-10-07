package br.com.unip.carrinho.service

import br.com.unip.carrinho.dto.ProdutoDTO
import br.com.unip.carrinho.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.carrinho.exception.NaoEncontradoException
import br.com.unip.carrinho.repository.IProdutoRepository
import br.com.unip.carrinho.repository.entity.Produto
import org.springframework.stereotype.Service

@Service
class ProdutoService(val produtoRepository: IProdutoRepository) : IProdutoService {

    override fun buscar(id: String): ProdutoDTO {
        val produto = buscarProduto(id)
        return ProdutoDTO(produto.id, produto.nome, produto.valor)
    }

    override fun buscarProduto(id: String): Produto {
        return produtoRepository.findById(id).orElseThrow { NaoEncontradoException(PRODUTO_NAO_ENCONTRADO) }
    }
}