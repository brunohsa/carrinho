package br.com.unip.carrinho.service


import br.com.unip.carrinho.dto.CadastroDTO
import org.springframework.stereotype.Service

@Service
class AutenticacaoService(val restService: IRestService) : IAutenticacaoService {

    override fun buscarCadastroPorEmail(email: String): CadastroDTO {
        return restService.get("http://localhost:8081/autenticacao/api/usuarios/$email/cadastro", CadastroDTO::class)
    }
}