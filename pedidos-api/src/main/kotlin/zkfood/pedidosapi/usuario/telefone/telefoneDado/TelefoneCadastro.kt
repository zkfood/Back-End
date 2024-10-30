package zkfood.pedidosapi.usuario.telefone.telefoneDado

import jakarta.validation.constraints.Size

data class TelefoneCadastro (
    @field:Size(min = 8, max = 12)
    var numero:String
)