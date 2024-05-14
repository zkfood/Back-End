package zkfood.pedidosapi.telefone.telefone.telefoneDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.NumberFormat

@Entity
data class Telefone (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    @field:Size(min = 10, max = 11)
    var numero:String? = null
){
}