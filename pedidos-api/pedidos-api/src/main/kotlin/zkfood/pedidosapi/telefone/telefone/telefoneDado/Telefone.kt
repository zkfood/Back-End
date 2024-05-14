package zkfood.pedidosapi.telefone.telefone.telefoneDado

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.NumberFormat
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@Entity
data class Telefone (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    @field:Size(min = 10, max = 11)
    var numero:String? = null,

    @field:ManyToOne
    var usuario:Usuario? = null
){
}