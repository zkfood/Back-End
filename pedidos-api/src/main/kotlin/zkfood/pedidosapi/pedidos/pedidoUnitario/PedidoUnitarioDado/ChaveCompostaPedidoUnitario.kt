package zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ChaveCompostaPedidoUnitario(
    @Column(name = "pedido_id")
    var pedido: Int? = null,

    @Column(name = "produto_id")
    var produto: Int? = null,
): Serializable