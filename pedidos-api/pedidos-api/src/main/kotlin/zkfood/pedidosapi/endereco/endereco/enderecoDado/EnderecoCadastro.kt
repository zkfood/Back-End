package zkfood.pedidosapi.endereco.endereco.enderecoDado

import jakarta.validation.constraints.Size

data class EnderecoCadastro(
    @field:Size(min = 8, max = 8)
    var cep:String,

    @field:Size(min = 5)
    var rua:String,

    @field:Size(min = 5)
    var bairro:String,

    @field:Size(min = 1)
    var numero:String
) {
}