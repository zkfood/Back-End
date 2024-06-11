package zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.IdClass

@Entity
data class PedidoUnitario(
    @EmbeddedId
    var id: ChaveCompostaPedidoUnitario? = null,

    var quantidade: Int? = null,

    var observacao: String? = null
)