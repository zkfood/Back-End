package zkfood.pedidosapi.pedidos.pedidoUnitario

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.PedidoUnitario

@RestController
@RequestMapping("/pedido-unitario")
class PedidoUnitarioControlador(
    val pedidoUnitarioServico: PedidoUnitarioServico
) {
    // TODO: Rever esse método, parece não estar funcionando
    @PatchMapping
    fun atualizar(@RequestBody pedidoUnitario: PedidoUnitario): ResponseEntity<PedidoUnitario> {
        val pedidoAtualizado = pedidoUnitarioServico.atualizar(pedidoUnitario);

        return ResponseEntity.status(200).body(pedidoAtualizado);
    }
}