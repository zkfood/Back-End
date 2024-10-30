package zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado

data class ReceitaAnoMesesResultadoConsulta (
    var tipoEntrega: String,

    var valores: MutableList<Double>
)