package zkfood.pedidosapi.usuario.usuario.usuarioDado

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UsuarioCadastro (
    @field:NotBlank
    var nome:String,

    @field:NotBlank @field:Email
    var email:String,

    @field:NotBlank
    var senha:String,

    @field:NotBlank @field:Size(min = 11, max = 11)
    var cpf:String
)