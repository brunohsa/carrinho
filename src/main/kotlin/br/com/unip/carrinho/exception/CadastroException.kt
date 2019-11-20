package br.com.unip.carrinho.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class CadastroException : RuntimeException {

    var codigoErro: ECodigoErro

    var httpStatus: HttpStatus

    constructor(codigoErro: ECodigoErro,
                httpStatus: HttpStatus) {
        this.codigoErro = codigoErro
        this.httpStatus = httpStatus
    }

    constructor(codigoErro: ECodigoErro,
                httpStatus: HttpStatus,
                message: String) : super(message) {
        this.codigoErro = codigoErro
        this.httpStatus = httpStatus
    }
}
