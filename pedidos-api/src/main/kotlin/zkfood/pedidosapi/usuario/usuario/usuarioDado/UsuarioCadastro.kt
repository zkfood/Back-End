package zkfood.pedidosapi.usuario.usuario.usuarioDado

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF

data class UsuarioCadastro (
    @field:NotBlank
    var nome: String,

    @field:Email
    var email: String,

    var senha: String? = null,

    @field:NotBlank @field:CPF
    var cpf: String,

    @field:NotNull
    var eCadastroOnline: Boolean = false
)