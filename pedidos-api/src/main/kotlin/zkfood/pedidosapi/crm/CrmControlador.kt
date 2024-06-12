package zkfood.pedidosapi.crm

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crm")
class CrmControlador (
    val crmServico: CrmServico
) {
    @GetMapping("/dashboard-produtos")
    fun dashboardProdutos(): ResponseEntity<List<Any>> {
        val listaDashboardProdutos = crmServico.dashboardProdutos();

        return ResponseEntity.status(200).body(listaDashboardProdutos);
    }

    @GetMapping("/kpis-produtos")
    fun kpisProdutos(): ResponseEntity<Any> {
        val listaDashboardProdutos = crmServico.kpisProdutos();

        return ResponseEntity.status(200).body(listaDashboardProdutos);
    }
}