package zkfood.pedidosapi.produto.produtoDado

import jakarta.validation.constraints.NotBlank

data class NovoValor (
    @field:NotBlank
    var valor:Double
) {
}