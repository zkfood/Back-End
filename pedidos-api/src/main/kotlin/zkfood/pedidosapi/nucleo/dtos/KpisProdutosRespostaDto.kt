package zkfood.pedidosapi.nucleo.dtos

data class KpisProdutosRespostaDto(
    var top3Melhores: List<DashboardProdutosRespostaDto>? = null,

    var top3Piores: List<DashboardProdutosRespostaDto>? = null
)
