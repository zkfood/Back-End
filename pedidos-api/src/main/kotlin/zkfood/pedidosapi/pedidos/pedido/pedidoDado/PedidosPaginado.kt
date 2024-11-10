package zkfood.pedidosapi.pedidos.pedido.pedidoDado

import zkfood.pedidosapi.nucleo.dtos.Paginacao

data class PedidosPaginado(
    var pedidos: List<PedidoCompletoResposta> = ArrayList(),
    var paginacao: Paginacao
)
