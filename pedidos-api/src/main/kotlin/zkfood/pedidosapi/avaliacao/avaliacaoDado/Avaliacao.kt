package zkfood.pedidosapi.avaliacao.avaliacaoDado

import jakarta.persistence.*

@Entity
data class Avaliacao(
    @EmbeddedId
    var id:AvaliacaoId? = null,

    var favorito: Boolean = false,

    var qtdEstrelas: Int? = null,

    var descricao: String? = null
)