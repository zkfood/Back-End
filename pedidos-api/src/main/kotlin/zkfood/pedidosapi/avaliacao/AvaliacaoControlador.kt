package zkfood.pedidosapi.avaliacao

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoCadastro
import zkfood.pedidosapi.avaliacao.dto.AvaliacaoDTO
import zkfood.pedidosapi.produtos.produtoDado.Produto

@RestController
@RequestMapping("/avaliacoes")
class AvaliacaoControlador (
    val avaliacaoServico: AvaliacaoServico
){
    @PatchMapping("/usuario/{idUsuario}/produto/{idProduto}")
    fun cadastrarOuAtualiazar(
        @PathVariable idProduto: Int,
        @PathVariable idUsuario: Int,
        @RequestBody avaliacaoDTO: AvaliacaoDTO
    ): ResponseEntity<Avaliacao> {
        val avaliacao = avaliacaoServico.cadastrarOuAtualiazar(idProduto, idUsuario, avaliacaoDTO)
        return ResponseEntity.ok(avaliacao)
    }

    @GetMapping
    fun listarAvaliacoes(): ResponseEntity<List<Avaliacao>> {
        val avaliacoes = avaliacaoServico.listarAvaliacoes()
        return if (avaliacoes.isNotEmpty()) {
            ResponseEntity.status(200).body(avaliacoes)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    fun listarAvaliacaoPorUsuario(@PathVariable usuarioId: Int): ResponseEntity<List<Avaliacao>> {
        val avaliacoes = avaliacaoServico.listarAvaliacaoPorUsuario(usuarioId)
        return if (avaliacoes.isNotEmpty()) {
            ResponseEntity.status(200).body(avaliacoes)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @GetMapping("/usuario/{usuarioId}/produto/{produtoId}")
    fun listarAvaliacaoPorId(@PathVariable usuarioId: Int, @PathVariable produtoId: Int): ResponseEntity<Avaliacao> {
        val avaliacao = avaliacaoServico.listarAvaliacaoPorId(usuarioId, produtoId)
        return if (avaliacao != null) {
            ResponseEntity.status(200).body(avaliacao)
        } else {
            ResponseEntity.status(404).build()
        }
    }

    @DeleteMapping("/usuario/{usuarioId}/produto/{produtoId}")
    fun deletar(@PathVariable usuarioId: Int, @PathVariable produtoId: Int): ResponseEntity<Void> {
        val avaliacao = avaliacaoServico.listarAvaliacaoPorId(usuarioId, produtoId)
        return if (avaliacao != null) {
            avaliacaoServico.deletar(usuarioId, produtoId)
            ResponseEntity.status(200).build()
        } else {
            ResponseEntity.status(404).build()
        }
    }
}