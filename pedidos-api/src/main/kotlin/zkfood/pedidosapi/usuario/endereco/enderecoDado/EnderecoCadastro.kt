package zkfood.pedidosapi.usuario.endereco.enderecoDado

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class EnderecoCadastro(
    @field:NotBlank @field:Size(min = 8, max = 8)
    var cep:String,

    @field:NotBlank
    var rua:String,

    @field:NotBlank
    var bairro:String,

    @field:NotBlank
    var numero:String,

    @field:NotBlank
    var complemento:String,
)