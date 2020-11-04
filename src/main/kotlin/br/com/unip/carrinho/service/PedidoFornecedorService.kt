package br.com.unip.carrinho.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.carrinho.dto.FiltroPedidoDTO
import br.com.unip.carrinho.dto.PedidoDTO
import br.com.unip.carrinho.repository.IPedidoRepository
import org.springframework.stereotype.Service


@Service
class PedidoFornecedorService(val pedidoRepository: IPedidoRepository) : IPedidoFornecedorService, PedidoService() {

    override fun buscarPedidos(filtro: FiltroPedidoDTO): List<PedidoDTO> {
        return super.buscarPedidos(filtro, AuthenticationUtil.getCadastroUUID()!!, "fornecedorUUID")
    }

    override fun concluido(pedidoId: String) {
        val pedido = super.buscarPedido(pedidoId, AuthenticationUtil.getCadastroUUID()!!)
        pedido.paraConcluido()

        pedidoRepository.save(pedido)
    }
}