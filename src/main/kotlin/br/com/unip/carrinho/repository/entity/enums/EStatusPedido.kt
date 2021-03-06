package br.com.unip.carrinho.repository.entity.enums

enum class EStatusPedido {

    PENDENTE_PAGAMENTO, PENDENTE_PREPARACAO, PREPARANDO, CONCLUIDO, CANCELADO;

    companion object {
        fun getStatus(status: String): EStatusPedido? {
            return values().find { v -> v.name == status }
        }
    }

    fun isConcluido(): Boolean {
        return this == CONCLUIDO
    }
}