package zkfood.pedidosapi.usuario.usuarioDado

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.GeneratedValue
import jakarta.validation.constraints.NotBlank
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull

data class Usuario (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY) val id:Int,
    @field:NotBlank var nome:String,
    @field:NotBlank var email:String,
    @JsonIgnore var senha:String,
    @field:NotNull var cpf:Int
)