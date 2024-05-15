package zkfood.pedidosapi.usuario.usuario.endereco.enderecoDado

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class EnderecoCadastro(
    @field:NotBlank @field:Size(min = 8, max = 8)
    var cep:String,

    @field:NotBlank @field:Size(min = 3)
    var rua:String,

    @field:NotBlank @field:Size(min = 2)
    var bairro:String,

    @field:NotBlank @field:Size(min = 1)
    var numero:String
) {
}