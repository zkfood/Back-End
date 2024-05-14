package zkfood.pedidosapi.telefone.telefone.telefoneDado

import org.springframework.format.annotation.NumberFormat

data class TelefoneCadastro (
    @field:NumberFormat
    var numero:Int,
)