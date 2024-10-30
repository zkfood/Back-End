package zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado

data class TopReceitasRespostaDto(
    var produto: String,
    var quantidadeVendida: Int,
    var receita: Double
)