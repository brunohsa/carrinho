package br.com.unip.carrinho.exception

import br.com.unip.carrinho.exception.ECodigoErro.ACESSO_NEGADO
import br.com.unip.carrinho.exception.ECodigoErro.ERRO_INESPERADO
import br.com.unip.carrinho.webservice.model.response.erro.Erro
import br.com.unip.carrinho.webservice.model.response.erro.ResponseError
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.Locale

@ControllerAdvice
class HandlerException {

    @ControllerAdvice
    class HandlerException(val messageSource: MessageSource) {

        private val PT = "pt"
        private val BR = "BR"

        @ExceptionHandler(Throwable::class)
        fun handlerErroInesperado(e: Throwable): ResponseEntity<ResponseError> {
            val erro = getErro(ERRO_INESPERADO)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseError(erro))
        }

        @ExceptionHandler(AccessDeniedException::class, AuthenticationCredentialsNotFoundException::class)
        fun handlerAcessoNegado(e: Exception): ResponseEntity<ResponseError> {
            val erro = getErro(ACESSO_NEGADO)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseError(erro))
        }

        @ExceptionHandler(CarrinhoBaseException::class)
        fun handlerCarrinhoException(e: CarrinhoBaseException): ResponseEntity<ResponseError> {
            val erro = getErro(e.codigoErro)
            return ResponseEntity.status(e.httpStatus.value()).body(ResponseError(erro))
        }

        private fun getErro(erro: ECodigoErro): Erro {
            return Erro(erro.codigo, getMensagem(erro))
        }

        private fun getMensagem(codigoErro: ECodigoErro): String {
            val local = Locale(PT, BR)
            return messageSource.getMessage(codigoErro.codigo, null, local)
        }
    }
}