package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class CarrinhoBaseException : RuntimeException {

    var codigoErro: ECodigoErro

    var httpStatus: HttpStatus

    constructor(codigoErro: ECodigoErro,
                httpStatus: HttpStatus) {
        this.codigoErro = codigoErro
        this.httpStatus = httpStatus
    }
}
