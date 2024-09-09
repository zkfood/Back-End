package zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado

data class TopPratos(
    var top4ou5: List<Map<String, Any>>,
    var top1ou2: List<Map<String, Any>>
)