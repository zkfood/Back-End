package zkfood.pedidosapi.produto.produtoDado

import jakarta.validation.constraints.NotBlank
import zkfood.pedidosapi.nucleo.enums.TipoProdutoEnum

data class ProdutoCadastro (
    @field:NotBlank
    var nome:String,

    @field:NotBlank
    var tipo: TipoProdutoEnum,

    @field:NotBlank
    var disponibilidade:Boolean,

    @field:NotBlank
    var descricao:String,

    @field:NotBlank
    var valorUnitario:Double,

    @field:NotBlank
    var qtdPessoas:Int
){
}