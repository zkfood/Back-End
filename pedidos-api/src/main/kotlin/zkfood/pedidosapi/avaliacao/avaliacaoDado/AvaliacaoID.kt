package zkfood.pedidosapi.avaliacao.avaliacaoDado

import java.io.Serializable
data class AvaliacaoId(
    var usuarioId: Int = 0,
    var produtoId: Int = 0
) : Serializable