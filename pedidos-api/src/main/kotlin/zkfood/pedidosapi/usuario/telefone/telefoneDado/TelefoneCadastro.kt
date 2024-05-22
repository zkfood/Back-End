package zkfood.pedidosapi.usuario.telefone.telefoneDado

import jakarta.validation.constraints.Size
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

data class TelefoneCadastro (
    @field:Size(min = 10, max = 11)
    var numero:String
)