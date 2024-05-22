package zkfood.pedidosapi.usuario.usuario.usuarioDado

import jakarta.validation.constraints.NotBlank

data class NovaSenha(
    @field:NotBlank
    var senha: String
)