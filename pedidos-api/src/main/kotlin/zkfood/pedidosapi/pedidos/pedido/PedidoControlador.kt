package zkfood.pedidosapi.pedidos.pedido

import jakarta.validation.Valid
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.nucleo.dtos.Paginacao
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.*
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.ProdutoUnitarioCadastro

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
    fun listarPedidos(
        @RequestParam idUsuario:Int,
        @RequestParam pagina:Int? = 1,
        @RequestParam tamanho:Int? = 2,
    ):ResponseEntity<PedidosPaginado>{
        val paginacao = Paginacao(pagina, tamanho);
        val pedidos = pedidoServico.listarPedidos(idUsuario, paginacao);

        return ResponseEntity.status(200).body(pedidos);
    }

    @GetMapping("/kanban")
    fun pedidosKanban(): ResponseEntity<List<PedidoCompletoResposta>>{
        val pedidos = pedidoServico.pedidosKanban();

        return ResponseEntity.status(200).body(pedidos);
    }

    @PostMapping
    fun cadastrar(@RequestBody @Valid novoPedido:PedidoCadastro):ResponseEntity<PedidoCompletoResposta>{
        val pedido = pedidoServico.cadastrarDeDTO(novoPedido);

        return ResponseEntity.status(201).body(pedido);
    }

    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id:Int, @RequestBody pedido:Pedido):ResponseEntity<PedidoCompletoResposta>{
        val pedidoAtualizado = pedidoServico.atualizarPedido(id, pedido);

        return ResponseEntity.status(200).body(pedidoAtualizado);
    }

    @PatchMapping("/{id}/adicionar-produtos")
    fun adicionarProdutos(@PathVariable id: Int, @RequestBody pedido: PedidoAdicionarProduto):ResponseEntity<PedidoCompletoResposta> {
        val pedidoAtualizado = pedidoServico.adicionarProdutos(id, pedido.listaProdutos);

        return ResponseEntity.status(200).body(pedidoAtualizado)
    }

    @DeleteMapping("/deletar-produto/{idProdutoUnitario}")
    fun deletarProduto(@PathVariable idProdutoUnitario: Int): ResponseEntity<Void> {
        pedidoServico.deletarProduto(idProdutoUnitario);

        return ResponseEntity.status(204).build();
    }
}