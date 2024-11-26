package zkfood.pedidosapi.relatorios.arquivos.csv

import java.time.LocalDate

data class SaidasDoDiaDto (
    var data: LocalDate,
    var quantidade: Int,
    var produto: String,
    var valor: Double,
    var receita: Double,
    var formaDePagamento: String?
)