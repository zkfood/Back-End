package zkfood.pedidosapi.produtos.tipoProduto.tipoDado

import jakarta.validation.constraints.NotBlank

data class TipoCadastro (
    @field:NotBlank
    var nome:String
)