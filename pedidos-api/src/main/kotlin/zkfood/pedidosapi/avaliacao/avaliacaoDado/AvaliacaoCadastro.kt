package zkfood.pedidosapi.avaliacao.avaliacaoDado

import jakarta.validation.constraints.NotBlank

data class AvaliacaoCadastro(
    @field:NotBlank
    var usuarioId: Int,

    @field:NotBlank
    var produtoId: Int,

    @field:NotBlank
    var favorito: Boolean,

    @field:NotBlank
    var qtdEstrelas: Int,

    @field:NotBlank
    var descricao: String
){

}