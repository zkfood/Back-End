package zkfood.pedidosapi.relatorios.avaliacoes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado.NuvemDePalavras
import zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado.QuantiadeAvaliacoesPorEstrelas
import zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado.TopPratos

@RestController
@RequestMapping("/relatorios/avaliacoes")
class RelatorioAvaliacoesControlador(
    val relatorioAvaliacoesServico: RelatorioAvaliacoesServico
) {

    @GetMapping("/top-pratos")
    fun topPratos(): ResponseEntity<TopPratos> {
        val topPratos = relatorioAvaliacoesServico.topPratos()

        return ResponseEntity.status(200).body(topPratos);
    }

    @GetMapping("/avaliacoes-por-estrela")
    fun quantidadeAvaliacoesPorEstrelas(): ResponseEntity<List<Int?>> {
        val quantidadeAvaliacoesPorEstrelas = relatorioAvaliacoesServico.quantidadeAvaliacoesPorEstrelas()

        return ResponseEntity.status(200).body(quantidadeAvaliacoesPorEstrelas);
    }

    @GetMapping("/nuvem-de-palavras")
    fun nuvemDePalavras(): ResponseEntity<NuvemDePalavras> {
        val nuvemDePalavras = relatorioAvaliacoesServico.nuvemDePalavras()

        return ResponseEntity.status(200).body(nuvemDePalavras);
    }
}