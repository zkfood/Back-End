package zkfood.pedidosapi.relatorios

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relatorios")
class RelatorioControlador (
    val relatorioServico: RelatorioServico
) {
    @GetMapping("/dashboard-produtos")
    fun dashboardProdutos(): ResponseEntity<List<Any>> {
        val listaDashboardProdutos = relatorioServico.dashboardProdutos();

        return ResponseEntity.status(200).body(listaDashboardProdutos);
    }

    @GetMapping("/kpis-produtos")
    fun kpisProdutos(): ResponseEntity<Any> {
        val listaDashboardProdutos = relatorioServico.kpisProdutos();

        return ResponseEntity.status(200).body(listaDashboardProdutos);
    }
}