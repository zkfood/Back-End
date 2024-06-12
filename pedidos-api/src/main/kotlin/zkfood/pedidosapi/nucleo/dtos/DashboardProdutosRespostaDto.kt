package zkfood.pedidosapi.nucleo.dtos

data class DashboardProdutosRespostaDto(
    var nome: String? = null,

    var quantidadeVenda: Int? = null,
)
// TODO: pensar em uma arquitetura melhor para guardar essas dtos