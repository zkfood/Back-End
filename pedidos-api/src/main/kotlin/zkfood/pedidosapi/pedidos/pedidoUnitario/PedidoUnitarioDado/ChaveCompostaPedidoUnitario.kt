package zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado

import java.io.Serializable

data class ChaveCompostaPedidoUnitario(
    var idPedido: Int? = null,

    var idProduto: Int? = null,
): Serializable