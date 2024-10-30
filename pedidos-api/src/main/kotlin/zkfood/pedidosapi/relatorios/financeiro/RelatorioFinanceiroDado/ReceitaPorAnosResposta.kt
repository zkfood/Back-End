package zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado

data class ReceitaPorAnosResposta(
    var anos: MutableList<Int> = mutableListOf(),
    var receitaPresencial: MutableList<Double> = mutableListOf(),
    var receitaBalcao: MutableList<Double> = mutableListOf(),
    var receitaEntrega: MutableList<Double> = mutableListOf()
)