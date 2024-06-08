package zkfood.pedidosapi.avaliacao.avaliacaoDado

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@IdClass(AvaliacaoId::class)
data class Avaliacao(
    @Id
    val usuarioId: Int,

    @Id
    val produtoId: Int,

    @field:NotNull
    var favorito: Boolean = false,

    @field:NotNull
    @field:Min(1)
    @field:Max(5)
    var qtdEstrelas: Int? = null,

    @field:Size(min = 5, max = 255)
    var descricao: String? = null
){
    constructor() : this(0, 0, false, null, null)
}