package zkfood.pedidosapi.avaliacao

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoDto
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId
import zkfood.pedidosapi.avaliacao.dto.AvaliacaoRespostaDto

@RestController
@RequestMapping("/avaliacoes")
class AvaliacaoControlador (
    val avaliacaoServico: AvaliacaoServico
){
    @PostMapping()
    fun salvar(@RequestBody novaAvaliacao: AvaliacaoDto): ResponseEntity<Avaliacao> {
        val avaliacao = avaliacaoServico.salvar(novaAvaliacao);

        return ResponseEntity.status(200).body(avaliacao);
    }

    @GetMapping
    fun listarAvaliacoes(
        @RequestParam usuario: Int? = null,
        @RequestParam produto: Int? = null,
        @RequestParam favorito: Boolean? = null,
        @RequestParam qtdEstrelas: Int? = null,
        @RequestParam descricao: String? = null,
    ): ResponseEntity<List<AvaliacaoRespostaDto>> {
        val avaliacaoDto = AvaliacaoDto(
            usuario = usuario,
            produto = produto,
            favorito = favorito,
            qtdEstrelas = qtdEstrelas,
            descricao = descricao
        )
        val avaliacoes = avaliacaoServico.listarAvaliacoes(avaliacaoDto);

        return ResponseEntity.status(200).body(avaliacoes);
    }

    @DeleteMapping
    fun deletar(@RequestParam usuario: Int, @RequestParam produto: Int): ResponseEntity<Void> {
        avaliacaoServico.deletar(usuario, produto);

        return ResponseEntity.status(204).build();
    }
}