package zkfood.pedidosapi.endereco.endereco.enderecoDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Size

@Entity
data class Endereco(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    @field:Size(min = 8, max = 8)
    var cep:String? = null,

    @field:Size(min = 5)
    var rua:String? = null,

    @field:Size(min = 5)
    var bairro:String? = null,

    @field:Size(min = 1)
    var numero:String? = null
) {
}