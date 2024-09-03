package zkfood.pedidosapi.usuario.usuario.usuarioDado

import jakarta.validation.constraints.NotBlank

// Apenas para entrada
data class NovaSenha(
    @field:NotBlank
    var senha: String
)