package zkfood.pedidosapi.pedidos.pedido.pedidoDado

import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.ProdutoUnitarioCadastro

data class PedidoAdicionarProduto(
    var listaProdutos: List<ProdutoUnitarioCadastro>
)