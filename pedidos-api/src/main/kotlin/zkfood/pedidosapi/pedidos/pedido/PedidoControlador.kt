package zkfood.pedidosapi.pedidos.pedido

import jakarta.validation.Valid
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.Pedido
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.PedidoCadastro
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.PedidoCompletoResposta

@RestController
@RequestMapping("/pedidos")
class PedidoControlador(
    val pedidoServico: PedidoServico
) {
    @GetMapping("/{id}")
    fun procurarPorId(@PathVariable id:Int): ResponseEntity<Any>{
        val pedido = pedidoServico.procurarPorId(id);

        return ResponseEntity.status(200).body(pedido);
    }

    @GetMapping
    fun listarPedidos(@RequestParam idUsuario:Int):ResponseEntity<List<PedidoCompletoResposta>>{
        val pedidoAtualizado = pedidoServico.listarPedidos(idUsuario);

        return ResponseEntity.status(200).body(pedidoAtualizado);
    }

    @PostMapping
    fun cadastrar(@RequestBody @Valid novoPedido:PedidoCadastro):ResponseEntity<PedidoCompletoResposta>{
        val pedido = pedidoServico.cadastrar(novoPedido);

        return ResponseEntity.status(201).body(pedido);
    }

    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id:Int, @RequestBody pedido:Pedido):ResponseEntity<PedidoCompletoResposta>{
        val pedidoAtualizado = pedidoServico.atualizarPedido(id, pedido);

        return ResponseEntity.status(200).body(pedidoAtualizado);
    }
}