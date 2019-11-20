package br.com.unip.carrinho.exception

import br.com.unip.carrinho.webservice.model.response.Erro
import br.com.unip.carrinho.webservice.model.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HandlerException {

    @ExceptionHandler(CadastroException::class)
    fun handlerCadastroException(e: CadastroException): ResponseEntity<Response> {
        val erro = Erro(e.codigoErro, e.message ?: "")
        return ResponseEntity.status(e.httpStatus.value()).body(Response(erro))
    }

    @ExceptionHandler(Throwable::class)
    fun handlerErroInesperado(e: Throwable): ResponseEntity<Response> {
        val erro = Erro(ECodigoErro.CAD000, "Erro inesperado")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response(erro))
    }
}