package br.com.unip.cardapio.security.util

import org.springframework.security.core.context.SecurityContextHolder

class AutenthicationUtil {

    companion object {
        fun getUsuarioLogado(): String {
            val auth = SecurityContextHolder.getContext().authentication
            return if (auth == null) "" else auth.name
        }
    }
}