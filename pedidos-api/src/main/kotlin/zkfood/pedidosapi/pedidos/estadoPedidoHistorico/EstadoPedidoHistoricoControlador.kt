package zkfood.pedidosapi.pedidos.estadoPedidoHistorico

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.pedidos.estadoPedidoHistorico.EstadoPedidoHistoricoDado.EstadoPedidoHistorico

@RestController
@RequestMapping("/estado-pedido-historico")
class EstadoPedidoHistoricoControlador(
    val estadoPedidoHistoricoServico: EstadoPedidoHistoricoServico
) {
    // TODO: colocar isso no domínio de pedidos
    @PatchMapping
    fun atualizar(@RequestBody estadoPedidoHistorico: EstadoPedidoHistorico): ResponseEntity<EstadoPedidoHistorico> {
        val pedidoAtualizado = estadoPedidoHistoricoServico.atualizar(estadoPedidoHistorico);

        return ResponseEntity.status(200).body(pedidoAtualizado);
    }
}