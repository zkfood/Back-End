package zkfood.pedidosapi.avaliacao.avaliacaoDado

import jakarta.validation.constraints.NotBlank

data class AvaliacaoDto(
    @field:NotBlank
    var usuario: Int? = null,

    @field:NotBlank
    var produto: Int? = null,

    @field:NotBlank
    var favorito: Boolean? = null,

    @field:NotBlank
    var qtdEstrelas: Int? = null,

    @field:NotBlank
    var descricao: String? = null
)