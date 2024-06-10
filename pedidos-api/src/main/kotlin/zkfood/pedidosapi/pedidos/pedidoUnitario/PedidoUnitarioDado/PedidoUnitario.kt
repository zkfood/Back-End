package zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass

@Entity
@IdClass(ChaveCompostaPedidoUnitario::class)
data class PedidoUnitario(
    @field:Id
    var idPedido: Int? = null,

    @field:Id
    var idProduto: Int? = null,

    var quantidade: Int? = null,

    var observacao: String? = null
)