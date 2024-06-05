package zkfood.pedidosapi.avaliacao.avaliacaoDado

import java.io.Serializable
data class AvaliacaoId(
    val usuarioId: Long = 0,
    val produtoId: Long = 0
) : Serializable