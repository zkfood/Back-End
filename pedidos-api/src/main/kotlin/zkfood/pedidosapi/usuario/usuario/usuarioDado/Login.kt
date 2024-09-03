package zkfood.pedidosapi.usuario.usuario.usuarioDado

import jakarta.validation.constraints.Email

// Apenas para entrada
data class Login(
    @field:Email
    var email: String? = null,

    var senha: String? = null,
)