package zkfood.pedidosapi.avaliacao.dto

data class AvaliacaoDTO(
    val favorito: Boolean,
    val qtdEstrelas: Int,
    val descricao: String
)