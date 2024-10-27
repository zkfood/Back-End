package zkfood.pedidosapi.nucleo.enums

import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

enum class EstadoPedidoEnum(val estado: String) {
    PEDIDO_EM_ESPERA("Pedido em espera"),
    PEDIDO_ACEITO("Pedido aceito"),
    PEDIDO_EM_PREPARO("Pedido em preparo"),
    PEDIDO_A_CAMINHO("Pedido a caminho"),
    PEDIDO_ENTREGUE("Pedido entregue"),
    PEDIDO_CANCELADO("Pedido cancelado"),
    PRODUTO_ADICIONADO_AO_PEDIDO("Produto adicionado ao pedido, em preparo");

    companion object {
        fun identificarEstado(tipo: String):EstadoPedidoEnum {
            entries.forEach {
                if (it.estado == tipo) return it;
            }
            throw ResponseStatusException(HttpStatusCode.valueOf(422));
        }
    }
}