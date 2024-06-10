package zkfood.pedidosapi.nucleo.dtos

import java.time.LocalDateTime

data class EstadoPedidoHistoricoSimpesRespostaDto(
    var id:Int? = null,

    var estado:String? = null,

    var hora: LocalDateTime? = null
)