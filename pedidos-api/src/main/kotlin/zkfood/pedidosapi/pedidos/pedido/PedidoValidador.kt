package zkfood.pedidosapi.pedidos.pedido

import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.PedidoCadastro

abstract class PedidoValidador {
    companion object {
        fun validarIds(pedidoCadastro: PedidoCadastro) {
            if (
                pedidoCadastro.usuario == null &&
                pedidoCadastro.colaborador == null &&
                pedidoCadastro.endereco == null &&
                pedidoCadastro.telefone == null
            ) throw ResponseStatusException(HttpStatusCode.valueOf(204));
        }
    }
}