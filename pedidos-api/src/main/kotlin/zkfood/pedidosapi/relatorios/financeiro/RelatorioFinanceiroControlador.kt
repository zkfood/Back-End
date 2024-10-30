package zkfood.pedidosapi.relatorios.financeiro

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado.KpisFinanceiroDto
import zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado.ReceitaPorAnosResposta
import zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado.TopReceitasRespostaDto

@RestController
@RequestMapping("/relatorios/financeiro")
class RelatorioFinanceiroControlador(
    val relatorioFinanceiroServico: RelatorioFinanceiroServico
) {

    @GetMapping("/kpis")
    fun kpis(@RequestParam data: String): ResponseEntity<List<Map<String, Number>>> {
        val dados = relatorioFinanceiroServico.kpis(data);

        return ResponseEntity.status(200).body(dados);
    }

    @GetMapping("/receita-ano-meses")
    fun receitaAnoMeses(@RequestParam ano: Int): ResponseEntity<List<Any>>{
        val dados = relatorioFinanceiroServico.receitaAnoMeses(ano);

        return ResponseEntity.status(200).body(dados);
    }

    @GetMapping("/receita-por-anos")
    fun receitaPorAnos(): ResponseEntity<ReceitaPorAnosResposta>{
        val dados = relatorioFinanceiroServico.receitaPorAnos()

        return ResponseEntity.status(200).body(dados);
    }

    @GetMapping("/top-receitas")
    fun topReceitas(
        @RequestParam mes: Int,
        @RequestParam ano: Int
    ): ResponseEntity<List<TopReceitasRespostaDto>> {
        val dados = relatorioFinanceiroServico.topReceitas(mes, ano)

        return ResponseEntity.status(200).body(dados);
    }
}