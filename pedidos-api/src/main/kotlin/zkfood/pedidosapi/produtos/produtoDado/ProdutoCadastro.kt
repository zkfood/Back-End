package zkfood.pedidosapi.produtos.produtoDado

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProdutoCadastro (
    @field:NotBlank
    var nome:String,

    @field:NotNull
    var disponibilidade:Boolean = false,

    @field:NotBlank
    var descricao:String,

    @field:NotNull
    var valorUnitario:Double,

    @field:NotNull
    var qtdPessoas:Int
)