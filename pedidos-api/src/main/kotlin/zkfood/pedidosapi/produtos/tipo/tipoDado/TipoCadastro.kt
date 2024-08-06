package zkfood.pedidosapi.produtos.tipo.tipoDado

import jakarta.validation.constraints.NotBlank

data class TipoCadastro (
    @field:NotBlank
    var nome:String
)