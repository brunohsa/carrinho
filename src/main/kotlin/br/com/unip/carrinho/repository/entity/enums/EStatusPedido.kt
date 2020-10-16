package br.com.unip.carrinho.repository.entity.enums

enum class EStatusPedido {

    PENDENTE_PAGAMENTO, PENDENTE_PREPARACAO, CONCLUIDO;

    companion object {
        fun getStatus(status: String): EStatusPedido? {
            return values().find { v -> v.name == status }
        }
    }
}