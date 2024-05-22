package zkfood.pedidosapi.usuario.usuario.usuarioDado

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class Login(
    @field:Email
    var email: String? = null,

    var senha: String? = null,
)