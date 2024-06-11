package zkfood.pedidosapi.usuario.telefone.telefoneDado

import jakarta.validation.constraints.Size
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

data class TelefoneCadastro (
    @field:Size(min = 8, max = 9)
    var numero:String
)